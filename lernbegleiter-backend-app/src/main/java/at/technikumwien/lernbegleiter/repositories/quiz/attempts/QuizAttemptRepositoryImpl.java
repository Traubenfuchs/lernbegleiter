package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class QuizAttemptRepositoryImpl implements QuizAttemptRepositoryCustom {
    @Lazy
    @Autowired
    private QuizAttemptRepository quizAttemptRepository;
    @Autowired
    private QuizRunRepository quizRunRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public QuizAttemptEntity createQuizAttemptIfNotExists(String quizRunUUID) {
        return quizAttemptRepository.findByFkQuizRunUUIDAndFkStudentUuid(quizRunUUID, AuthHelper.getCurrentUserUUIDOrThrow())
                .orElseGet(() -> quizAttemptRepository.save(new QuizAttemptEntity()
                        .setQuizRun(quizRunRepository.getOne(quizRunUUID))
                        .setStudent(userRepository.getCurrentUser())));
    }
}
