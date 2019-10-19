package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface QuizRunRepository extends JpaRepository<QuizRunEntity, String> {
  Set<QuizRunEntity> getByFkQuizUuid(String fkQuizUuid);

  Set<QuizRunEntity> findByState(QuizRunState quizRunState);
}
