package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttemptEntity, String>, QuizAttemptRepositoryCustom {
  Optional<QuizAttemptEntity> findByFkQuizRunUUIDAndFkStudentUuid(String fkQuizRunUUID, String fkStudentUuid);

  @EntityGraph(value = "QuizAttempt.allAnswers", type = EntityGraph.EntityGraphType.FETCH)
  Set<QuizAttemptEntity> findAllAnswersByFkQuizRunUUID(String fkQUizRunUUID);

  boolean existsByFkQuizRunUUIDAndFkStudentUuid(String fkQuizRunUUID, String fkStudentUuid);
}
