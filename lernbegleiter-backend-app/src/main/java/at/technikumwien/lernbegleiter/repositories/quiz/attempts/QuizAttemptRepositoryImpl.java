package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Repository
public class QuizAttemptRepositoryImpl implements QuizAttemptRepositoryCustom {
  @Lazy
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizRunRepository quizRunRepository;
  @Autowired
  private UserRepository userRepository;
  @Lazy
  @Autowired
  private QuizRunService quizRunService;

  @Override
  public QuizAttemptEntity createQuizAttemptIfNotExists(String quizRunUUID) {
    return quizAttemptRepository.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow())
      .orElseGet(() -> {
        QuizAttemptEntity result = quizAttemptRepository.save(
          new QuizAttemptEntity()
            .setQuizRun(quizRunRepository.getOne(quizRunUUID))
            .setStudent(userRepository.getCurrentUser()));
        quizRunService.createQuestionAndAnswerAttemptsForQuizAttempt(result.getUuid());
        return result;
      });
  }
}
