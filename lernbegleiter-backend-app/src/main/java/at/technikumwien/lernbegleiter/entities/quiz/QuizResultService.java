package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import lombok.experimental.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.time.*;
import java.util.*;

@AllArgsConstructor
@Service
public class QuizResultService {
  private final QuizAttemptRepository quizAttemptRepository;
  private final QuizRepository quizRepository;
  private final QuizQuestionRepository quizQuestionRepository;


  boolean checkIfCorrect(QuizQuestionAttemptEntity qqa) {
    return switch (qqa.getQuizQuestion().getQuizQuestionType()) {
      case MULTIPLE_CHOICE:
        yield qqa.getAnswers().stream().allMatch(quizQuestionAnswerAttemptEntity ->
          quizQuestionAnswerAttemptEntity.getCorrect().equals(quizQuestionAnswerAttemptEntity.getQuizAnswer().getCorrect()));

      case FREE_TEXT:
        yield Objects.equals(qqa.getQuizQuestion().getFreeText(), qqa.getFreeText());

      default:
        throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Given QuizQuestionType<" + qqa.getQuizQuestion().getQuizQuestionType() + "> is unhandled.");
    };
  }

  public Map<String, QuizQuestionAnswerSpan> getAnswerSpans(Set<QuizAttemptEntity> quizAttempts) {
    final Map<String, QuizQuestionAnswerSpan> quizUuidToAnswerSpan = new HashMap();

    quizAttempts.forEach(qa -> {
      qa.getQuizQuestionAttempts().forEach(qqa -> {
        String currentQuizQuestionUuid = qqa.getFkQuizQuestionUuid();

        if (!checkIfCorrect(qqa)) {
          return; // = wrong answer to question
        }

        final long max = switch (qqa.getQuizQuestion().getQuizQuestionType()) {
          case MULTIPLE_CHOICE:
            yield qqa.getAnswers().stream()
              .map(QuizQuestionAnswerAttemptEntity::getAnsweredAt)
              .mapToLong(Instant::toEpochMilli)
              .max()
              .getAsLong();

          case FREE_TEXT:
            yield qqa.getAnsweredAt().toEpochMilli();
        };

        quizUuidToAnswerSpan.compute(qqa.getFkQuizQuestionUuid(), (key, qqas) -> {
          if (qqas == null) {
            return new QuizQuestionAnswerSpan()
              .setEarliest(max)
              .setLatest(max)
              .setQuizQuestionType(qqa.getQuizQuestion().getQuizQuestionType());
          }

          if (qqas.getEarliest() > max) {
            qqas.setEarliest(max);
          }

          if (qqas.getLatest() < max) {
            qqas.setLatest(max);
          }

          return qqas;
        });
      });
    });

    return quizUuidToAnswerSpan;
  }

  private QuizResultDto getResult(
    String quizUuid,
    Set<QuizAttemptEntity> quizAttempts,
    Map<String, QuizQuestionAnswerSpan> quizUuidToAnswerSpan) {
    QuizResultDto result = new QuizResultDto();

    Map<String, QuizAnswerEntity> uuidToQuizAnswerEntity = new HashMap<>();
    Set<QuizQuestionEntity> questions = quizQuestionRepository.findAllByFkQuizzUuid(quizUuid);
    questions.forEach(q -> q.getAnswers().forEach(a -> uuidToQuizAnswerEntity.put(a.getUuid(), a)));

    for (QuizAttemptEntity quizAttemptEntity : quizAttempts) {
      QuizResultEntryDto quizResultEntryDto = new QuizResultEntryDto();
      String name = "" +
        quizAttemptEntity.getStudent().getFirstName() +
        " " +
        quizAttemptEntity.getStudent().getFamilyName();
      quizResultEntryDto.setName("" +
        quizAttemptEntity.getStudent().getFirstName() +
        " " +
        quizAttemptEntity.getStudent().getFamilyName());

      question:
      for (QuizQuestionAttemptEntity quizQuestionAttemptEntity : quizAttemptEntity.getQuizQuestionAttempts()) {
        if (!checkIfCorrect(quizQuestionAttemptEntity)) {
          continue question;
        }

        QuizQuestionAnswerSpan qqas = quizUuidToAnswerSpan.get(quizQuestionAttemptEntity.getFkQuizQuestionUuid());
        if (qqas != null) {
          final long max = switch (qqas.getQuizQuestionType()) {
            case MULTIPLE_CHOICE:
              yield quizQuestionAttemptEntity.getAnswers().stream()
                .map(QuizQuestionAnswerAttemptEntity::getAnsweredAt)
                .mapToLong(Instant::toEpochMilli)
                .max().getAsLong();

            case FREE_TEXT:
              yield quizQuestionAttemptEntity.getAnsweredAt().toEpochMilli();
          };

          long span = (qqas.getLatest() - qqas.getEarliest());
          if (span == 0) {
            quizResultEntryDto.incrementWeightedPointsBy(1000);
          } else if (span > 0) {
            double perc = 1 - (max - qqas.getEarliest()) / (double) span;
            System.out.println(name + ", " + span + ", " + (max - 1578154000000L) + ", " + perc);

            quizResultEntryDto.incrementWeightedPointsBy((int) Math.round(499 + 501.0 * perc));
          }
        }

        quizResultEntryDto.incrementPoints();
      }

      result.getEntries().add(quizResultEntryDto);
    }

    result.getEntries().sort(Comparator.comparing(QuizResultEntryDto::getWeightedPoints));
    return result;
  }

  public synchronized QuizResultDto getQuizResult(@NonNull String quizUuid, @NonNull String quizRunUuid) {
    Set<QuizAttemptEntity> quizAttempts = quizAttemptRepository.findAllAnswersByFkQuizRunUUID(quizRunUuid);

    Map<String, QuizQuestionAnswerSpan> quizUuidToAnswerSpan = getAnswerSpans(quizAttempts);

    QuizResultDto result = getResult(quizUuid, quizAttempts, quizUuidToAnswerSpan);

    return result;
  }

  @Accessors(chain = true)
  @Data
  class QuizQuestionAnswerSpan {
    private Long earliest;
    private Long latest;
    private QuizQuestionType quizQuestionType;
  }
}
