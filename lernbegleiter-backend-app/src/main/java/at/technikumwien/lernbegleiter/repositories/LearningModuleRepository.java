package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.LearningModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningModuleRepository  extends JpaRepository<LearningModuleEntity, String> {
}
