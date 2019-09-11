package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizAttemptRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizQuestionAttemptRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizAnsweringService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;

  public void answer(@NonNull String quizRunUUID, @NonNull Integer position, @NonNull Boolean tick) {
    // 1. get/create QuizAttemptEntity
    // 2. change/create QuizQuestionAttemptEntity
  }
}
