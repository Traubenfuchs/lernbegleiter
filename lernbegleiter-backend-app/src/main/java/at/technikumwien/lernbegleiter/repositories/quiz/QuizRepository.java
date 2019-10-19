package at.technikumwien.lernbegleiter.repositories.quiz;

import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, String> {
  @EntityGraph(value = "Quiz.allAnswers", type = EntityGraph.EntityGraphType.FETCH)
  QuizEntity getByUuid(String uuid);
}
