package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Repository
public class QuizAttemptRepositoryImpl implements QuizAttemptRepositoryCustom {
  @Lazy
  @Autowired
  private QuizAttemptRepository self;
  @Autowired
  private QuizRunRepository quizRunRepository;
  @Autowired
  private UserRepository userRepository;
  @Lazy
  @Autowired
  private QuizRunService quizRunService;

  @Override
  public QuizAttemptEntity createQuizAttemptIfNotExists(String quizRunUUID) {
    if (self.existsByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow())) {
      return self.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow()).get();
    }

    self.createQuizAttemptForCurrentUser(quizRunUUID);

    return self.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow()).get();
  }

  @Override
  public Object createQuizAttemptForCurrentUser(String quizRunUUID) {
    QuizAttemptEntity result = self.save(
      new QuizAttemptEntity()
        .setQuizRun(quizRunRepository.getOne(quizRunUUID))
        .setStudent(userRepository.getCurrentUser()));

    quizRunService.createQuestionAndAnswerAttemptsForQuizAttempt(result.getUuid());

    return result;
  }
}
