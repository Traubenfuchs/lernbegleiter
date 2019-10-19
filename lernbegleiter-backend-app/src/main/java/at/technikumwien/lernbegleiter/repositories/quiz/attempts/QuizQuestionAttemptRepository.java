package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface QuizQuestionAttemptRepository extends JpaRepository<QuizQuestionAttemptEntity, String> {
  QuizQuestionAttemptEntity findByFkQuizAttemptUuidAndFkQuizQuestionUuid(String fkQuizAttemptUuid, String fkQuizQuestionUuid);
}
