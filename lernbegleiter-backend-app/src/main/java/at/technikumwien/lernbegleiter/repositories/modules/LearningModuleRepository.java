package at.technikumwien.lernbegleiter.repositories.modules;

import at.technikumwien.lernbegleiter.entities.modules.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface LearningModuleRepository extends JpaRepository<LearningModuleEntity, String> {
  Set<LearningModuleEntity> findByClazzUuid(String classUuid);
}
