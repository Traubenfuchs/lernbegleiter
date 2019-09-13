package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionAttemptService {
    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

}
