package at.technikumwien.lernbegleiter.repositories.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttemptEntity, String> {
    Optional<QuizAttemptEntity> findByFkQuizRunUUID(String fkQuizRunUUID);
}
