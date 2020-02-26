package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

public interface QuizAttemptRepositoryCustom {

  String createQuizAttemptIfNotExists(String quizRunUUID);

  String createQuizAttemptForCurrentUser(String quizRunUUID);
}
