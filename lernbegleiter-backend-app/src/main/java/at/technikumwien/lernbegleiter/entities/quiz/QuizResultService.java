package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.dto.QuizResultDto;
import at.technikumwien.lernbegleiter.data.dto.QuizResultEntryDto;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizQuestionAnswerAttemptEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizQuestionAttemptEntity;
import at.technikumwien.lernbegleiter.repositories.quiz.QuizRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizAttemptRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
