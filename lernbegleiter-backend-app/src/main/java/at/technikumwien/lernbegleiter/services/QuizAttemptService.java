package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.quiz.QuizAnswerDto;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizQuestionAttemptEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizAttemptRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizQuestionAttemptRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizRunRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizAttemptService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizRunRepository quizRunRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;

  /**
   * Adds live attempt data to the given QuizRunDto. In other words, this method populates the "correct" field in
   * QuizQuestionAnswers inside the current quizQuestion in the given QuizRunDto
   */
  public void enrichWithAttemptData(@NonNull QuizRunDto quizRunDto) {
    if (quizRunDto.getCurrentQuestion() == null) {
      return;
    }

    QuizQuestionAttemptEntity quizQuestionAttemptEntity = quizQuestionAttemptRepository.findByFkQuizAttemptUuidAndFkQuizQuestionUuid(
        createQuizAttemptIfNotExists(quizRunDto.getUuid()),
        quizRunDto.getCurrentQuestion().getUuid());

    for (QuizAnswerDto answerDto : quizRunDto.getCurrentQuestion().getAnswers()) {
      boolean correct = quizQuestionAttemptEntity.getAnswers()
          .stream()
          .filter(a -> a.getUuid().equals(answerDto.getUuid()))
          .findFirst()
          .get()
          .getCorrect();

      answerDto.setCorrect(correct);
    }
  }

  public String createQuizAttemptIfNotExists(String quizRunUUID) {
    return quizAttemptRepository.createQuizAttemptIfNotExists(quizRunUUID).getUuid();
  }
}
