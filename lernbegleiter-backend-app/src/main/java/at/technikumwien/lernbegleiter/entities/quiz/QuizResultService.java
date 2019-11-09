package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import lombok.experimental.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class QuizResultService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizRepository quizRepository;

  public QuizResultDto getQuizResult(@NonNull String quizUuid, @NonNull String quizRunUuid) {
    QuizResultDto result = new QuizResultDto();

    QuizEntity quizEntity = quizRepository.getByUuid(quizUuid);
    Map<String, QuizAnswerEntity> uuidToQuizAnswerEntity = new HashMap<>();
    quizEntity.getQuestions().forEach(q -> q.getAnswers().forEach(a -> uuidToQuizAnswerEntity.put(a.getUuid(), a)));

    Set<QuizAttemptEntity> quizAttempts = quizAttemptRepository.findAllAnswersByFkQuizRunUUID(quizRunUuid);


    Map<String, QuizQuestionAnswerSpan> quizUuidToAnswerSpan = new HashMap<>();
    quizAttempts.forEach(qa -> {
      qa.getQuizQuestionAttempts().forEach(qqa -> {
        String currentQuizQuestionUuid = qqa.getFkQuizQuestionUuid();

        if (!qqa.getAnswers().stream().allMatch(quizQuestionAnswerAttemptEntity ->
          quizQuestionAnswerAttemptEntity.getCorrect().equals(quizQuestionAnswerAttemptEntity.getQuizAnswer().getCorrect())
        )) {
          return;
        }

        long max = qqa.getAnswers().stream()
          .map(BaseEntityCreationUpdateDate::getTsUpdate)
          .mapToLong(Instant::toEpochMilli)
          .max().getAsLong();

        quizUuidToAnswerSpan.compute(qqa.getFkQuizQuestionUuid(), (key, qqas) -> {
          if (qqas == null) {
            return new QuizQuestionAnswerSpan().setEarliest(max).setLatest(max);
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

    for (QuizAttemptEntity quizAttemptEntity : quizAttempts) {
      QuizResultEntryDto quizResultEntryDto = new QuizResultEntryDto();
      String name = quizAttemptEntity.getStudent().getFirstName() + " " +
        quizAttemptEntity.getStudent().getFamilyName();
      quizResultEntryDto.setName(name);

      question:
      for (QuizQuestionAttemptEntity quizQuestionAttemptEntity : quizAttemptEntity.getQuizQuestionAttempts()) {

        for (QuizQuestionAnswerAttemptEntity quizQuestionAnswerAttemptEntity : quizQuestionAttemptEntity.getAnswers()) {
          QuizAnswerEntity quizAnswerEntity = uuidToQuizAnswerEntity.get(quizQuestionAnswerAttemptEntity.getFkQuizAnswerUuid());
          if (!quizQuestionAnswerAttemptEntity.getCorrect().equals(quizAnswerEntity.getCorrect())) {
            continue question;
          }
        }

        QuizQuestionAnswerSpan qqas = quizUuidToAnswerSpan.get(quizQuestionAttemptEntity.getFkQuizQuestionUuid());
        if (qqas != null) {
          long max = quizQuestionAttemptEntity.getAnswers().stream()
            .map(BaseEntityCreationUpdateDate::getTsUpdate)
            .mapToLong(Instant::toEpochMilli)
            .max().getAsLong();

          double d = (double) (qqas.getLatest() - qqas.getEarliest());
          if (d > 0) {
            double perc = (max - qqas.getEarliest()) / (double) (qqas.getLatest() - qqas.getEarliest());

            quizResultEntryDto.incrementWeightedPointsBy((int) Math.round(501 + 501.0 * perc));
          }
        }

        quizResultEntryDto.incrementPoints();
      }

      result.getEntries().add(quizResultEntryDto);
    }

    result.getEntries().sort(Comparator.comparing(QuizResultEntryDto::getPoints));

    return result;
  }

  @Accessors(chain = true)
  @Data
  class QuizQuestionAnswerSpan {
    private Long earliest;
    private Long latest;
  }
}
