package at.technikumwien.xxx.repositories;

import at.technikumwien.xxx.entities.ClassEntity;
import at.technikumwien.xxx.entities.LearningModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningModuleRepository  extends JpaRepository<LearningModuleEntity, String> {
}
