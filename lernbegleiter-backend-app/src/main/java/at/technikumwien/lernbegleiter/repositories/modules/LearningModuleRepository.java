package at.technikumwien.lernbegleiter.repositories.modules;

import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LearningModuleRepository extends JpaRepository<LearningModuleEntity, String> {
    Set<LearningModuleEntity> findByClazzUuid(String classUuid);
}
