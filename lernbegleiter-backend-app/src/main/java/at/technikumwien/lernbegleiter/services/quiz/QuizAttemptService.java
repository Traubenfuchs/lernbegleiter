package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.attempt.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import java.util.*;
import java.util.stream.*;


@Service
public class QuizAttemptService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;
  @Lazy
  @Autowired
  private QuizAttemptService quizAttemptService;

  /**
   * Adds live attempt data to the given QuizRunDto. In other words, this method populates the "correct" field in
   * QuizQuestionAnswers inside the current quizQuestion in the given QuizRunDto
   */
  @Transactional
  public void enrichWithAttemptData(@NonNull QuizRunDto quizRunDto) {
    //if (
    // CollectionUtils.isEmpty(quizRunDto.getCurrentQuestions()) || quizRunDto.getState() != QuizRunState.WAITING_FOR_ANSWERS) {
    //  return;
    // }

    if (CollectionUtils.isEmpty(quizRunDto.getCurrentQuestions())) {
      return;
    }
    Map<String, QuizQuestionAttemptEntity> quizQuestionAttempts =
      quizQuestionAttemptRepository
        .findByFkQuizAttemptUuid(createQuizAttemptIfNotExists(quizRunDto.getUuid()))
        .stream()
        .collect(Collectors.toMap(QuizQuestionAttemptEntity::getFkQuizQuestionUuid, x -> x));

    for (QuizQuestionDto currentQuestion : quizRunDto.getCurrentQuestions()) {
      QuizQuestionAttemptEntity quizQuestionAttemptEntity = quizQuestionAttempts.get(currentQuestion.getUuid());

      boolean questionAnsweredCorrectly = true;

      currentQuestion.setFreeText(quizQuestionAttemptEntity.getFreeText());

      for (QuizAnswerDto answerDto : currentQuestion.getAnswers()) {
        boolean ticked = quizQuestionAttemptEntity.getAnswers()
          .stream()
          .filter(a -> a.getFkQuizAnswerUuid().equals(answerDto.getUuid()))
          .findFirst()
          .get()
          .getCorrect();

        if (
          quizRunDto.getQuizRunType() == QuizRunType.FINISH_SELF ||
            quizRunDto.getState() == QuizRunState.DONE ||
            quizRunDto.getState() == QuizRunState.WAITING_FOR_NEXT_QUESTION) {
          boolean tickedCorrectly = answerDto.getCorrect().equals(ticked);
          if (!tickedCorrectly) {
            questionAnsweredCorrectly = false;
          }
          answerDto.setTickedCorrectly(answerDto.getCorrect().equals(ticked));
          currentQuestion.setAnsweredCorrectly(questionAnsweredCorrectly);
        }

        if (quizRunDto.getState() == QuizRunState.WAITING_FOR_ANSWERS) {
          answerDto.setCorrect(ticked);
        }
      }
    }
  }


  public String createQuizAttemptIfNotExists(String quizRunUUID) {
    DataIntegrityViolationException ex = null;
    for (int i = 0; i < 5; i++) {
      try {
        return quizAttemptRepository.createQuizAttemptIfNotExists(quizRunUUID);
      } catch (DataIntegrityViolationException e) {
        ex = e;
      }
    }
    throw ex;
  }

  @Transactional
  public void finishQuizAttempt(String quizRunUUID) {
    Optional<QuizAttemptEntity> quizAttemptOptional = quizAttemptRepository.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow());
    QuizAttemptEntity quizAttemptEntity = quizAttemptOptional.get();
    quizAttemptEntity.setQuizQuestionAttemptState(QuizQuestionAttemptState.FINISHED_BY_STUDENT);
  }

  public QuizAttemptDto getByQuizRunUuidAndCurrentUser(String quizRunUUID) {
    Optional<QuizAttemptEntity> quizAttemptOptional = quizAttemptRepository.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow());
    QuizAttemptEntity quizAttemptEntity = quizAttemptOptional.get();

    return new QuizAttemptDto()
      .setQuizQuestionAttemptState(quizAttemptEntity.getQuizQuestionAttemptState());
  }
}
