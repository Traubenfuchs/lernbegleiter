package at.technikumwien.lernbegleiter.repositories.quiz;

import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswerEntity, String> {
}
