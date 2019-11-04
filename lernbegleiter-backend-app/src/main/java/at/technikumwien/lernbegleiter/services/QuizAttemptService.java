package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

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
  public void enrichWithAttemptData(@NonNull QuizRunDto quizRunDto) {
    if (
      CollectionUtils.isEmpty(quizRunDto.getCurrentQuestions()) || quizRunDto.getState() != QuizRunState.WAITING_FOR_ANSWERS) {
      return;
    }

    for (QuizQuestionDto currentQuestion : quizRunDto.getCurrentQuestions()) {
      QuizQuestionAttemptEntity quizQuestionAttemptEntity = quizQuestionAttemptRepository.findByFkQuizAttemptUuidAndFkQuizQuestionUuid(
        createQuizAttemptIfNotExists(quizRunDto.getUuid()),
        currentQuestion.getUuid());

      for (QuizAnswerDto answerDto : currentQuestion.getAnswers()) {
        boolean correct = quizQuestionAttemptEntity.getAnswers()
          .stream()
          .filter(a -> a.getFkQuizAnswerUuid().equals(answerDto.getUuid()))
          .findFirst()
          .get()
          .getCorrect();

        answerDto.setCorrect(correct);
      }
    }
  }

  public String createQuizAttemptIfNotExists(String quizRunUUID) {
    return quizAttemptRepository.createQuizAttemptIfNotExists(quizRunUUID).getUuid();
  }
}
