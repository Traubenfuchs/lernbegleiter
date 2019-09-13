package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizAttemptRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizRunRepository;
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

    public String createQuizAttemptIfNotExists(String quizRunUUID) {
        return quizAttemptRepository.findByFkQuizRunUUID(quizRunUUID)
                .orElseGet(() -> quizAttemptRepository.save(new QuizAttemptEntity()
                        .setQuizRun(quizRunRepository.getOne(quizRunUUID))
                        .setStudent(userRepository.getCurrentUser())))
                .getUuid();
    }
}
