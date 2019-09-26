package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttemptEntity, String>, QuizAttemptRepositoryCustom {
  Optional<QuizAttemptEntity> findByFkQuizRunUUIDAndFkStudentUuid(String fkQuizRunUUID, String fkStudentUuid);

  @EntityGraph(value = "QuizAttempt.allAnswers", type = EntityGraph.EntityGraphType.FETCH)
  Set<QuizAttemptEntity> findAllAnswersByFkQuizRunUUID(String fkQUizRunUUID);
}
