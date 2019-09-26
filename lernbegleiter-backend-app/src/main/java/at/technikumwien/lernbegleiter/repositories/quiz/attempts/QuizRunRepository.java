package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuizRunRepository extends JpaRepository<QuizRunEntity, String> {
  Set<QuizRunEntity> getByFkQuizUuid(String fkQuizUuid);

  Set<QuizRunEntity> findByState(QuizRunState quizRunState);
}
