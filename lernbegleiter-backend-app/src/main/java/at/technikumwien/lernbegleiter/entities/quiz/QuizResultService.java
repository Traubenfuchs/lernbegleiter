package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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

        quizResultEntryDto.incrementPoints();
      }

      result.getEntries().add(quizResultEntryDto);
    }

    result.getEntries().sort(Comparator.comparing(QuizResultEntryDto::getPoints));

    return result;
  }
}
