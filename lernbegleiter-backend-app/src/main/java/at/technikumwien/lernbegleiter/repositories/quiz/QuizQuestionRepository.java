package at.technikumwien.lernbegleiter.repositories.quiz;

import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, String> {
}
