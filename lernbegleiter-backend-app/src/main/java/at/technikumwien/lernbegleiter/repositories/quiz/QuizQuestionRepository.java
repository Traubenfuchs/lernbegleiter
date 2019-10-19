package at.technikumwien.lernbegleiter.repositories.quiz;

import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, String> {
  Set<QuizQuestionEntity> findAllByFkQuizzUuid(String fkQuizUuid);
}
