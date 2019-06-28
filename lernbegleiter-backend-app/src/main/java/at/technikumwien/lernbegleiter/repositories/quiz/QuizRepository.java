package at.technikumwien.lernbegleiter.repositories.quiz;

import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, String> {
}
