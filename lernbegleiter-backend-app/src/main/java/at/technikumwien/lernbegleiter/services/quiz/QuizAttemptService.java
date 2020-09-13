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
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import java.util.*;

import static at.technikumwien.lernbegleiter.data.QuizRunState.*;
import static at.technikumwien.lernbegleiter.data.QuizRunType.*;


@Service
public class QuizAttemptService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;

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
    Map<String, QuizQuestionAttemptEntity> quizQuestionAttemptEntities =
      quizQuestionAttemptRepository.findByFkQuizAttemptUuidAsMap(createQuizAttemptIfNotExists(quizRunDto.getUuid()));

    for (QuizQuestionDto currentQuestion : quizRunDto.getCurrentQuestions()) {
      QuizQuestionAttemptEntity quizQuestionAttemptEntity = quizQuestionAttemptEntities.get(currentQuestion.getUuid());

      currentQuestion.setFreeText(quizQuestionAttemptEntity.getFreeText());

      switch (currentQuestion.getQuizQuestionType()) {
        case FREE_TEXT -> {
          String correctAnswer = quizQuestionAttemptEntity.getQuizQuestion().getFreeText();
          currentQuestion.setAnsweredCorrectly(correctAnswer.equalsIgnoreCase(quizQuestionAttemptEntity.getFreeText()));
        }
        case MULTIPLE_CHOICE -> handleMultipleChoice(currentQuestion, quizQuestionAttemptEntity, quizRunDto);
      }
    }
  }

  private void handleMultipleChoice(
    QuizQuestionDto currentQuestion,
    QuizQuestionAttemptEntity quizQuestionAttemptEntity,
    QuizRunDto quizRunDto
  ) {
    for (QuizAnswerDto answerDto : currentQuestion.getAnswers()) {
      boolean ticked = quizQuestionAttemptEntity.getAnswers()
        .stream()
        .filter(a -> a.getFkQuizAnswerUuid().equals(answerDto.getUuid()))
        .findFirst()
        .get()
        .getCorrect();

      if (
        quizRunDto.getQuizRunType() == FINISH_SELF ||
          quizRunDto.getState() == DONE ||
          quizRunDto.getState() == WAITING_FOR_NEXT_QUESTION) {
        boolean tickedCorrectly = answerDto.getCorrect().equals(ticked);
        answerDto.setTickedCorrectly(tickedCorrectly);
        currentQuestion.setAnsweredCorrectly(tickedCorrectly);
      }

      if (quizRunDto.getState() == QuizRunState.WAITING_FOR_ANSWERS) {
        answerDto.setCorrect(ticked);
      }
    }
  }


  public String createQuizAttemptIfNotExists(String quizRunUUID) {
    for (int i = 0; i < 5; i++) {
      return quizAttemptRepository.createQuizAttemptIfNotExists(quizRunUUID);
    }
    throw new RuntimeException("Should not happen.");
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
