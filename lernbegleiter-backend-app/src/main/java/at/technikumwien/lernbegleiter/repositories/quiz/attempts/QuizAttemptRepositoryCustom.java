package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;

public interface QuizAttemptRepositoryCustom {

  QuizAttemptEntity createQuizAttemptIfNotExists(String quizRunUUID);
}
