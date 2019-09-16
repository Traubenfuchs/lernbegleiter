package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class QuizAttemptRepositoryCustomImpl implements QuizAttemptRepositoryCustom {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizRunRepository quizRunRepository;
  @Autowired
  private UserRepository userRepository;

  @Override
  public QuizAttemptEntity createQuizAttemptIfNotExists(String quizRunUUID) {
    return quizAttemptRepository.findByFkQuizRunUUID(quizRunUUID)
        .orElseGet(() -> quizAttemptRepository.save(new QuizAttemptEntity()
            .setQuizRun(quizRunRepository.getOne(quizRunUUID))
            .setStudent(userRepository.getCurrentUser())));
  }
}
