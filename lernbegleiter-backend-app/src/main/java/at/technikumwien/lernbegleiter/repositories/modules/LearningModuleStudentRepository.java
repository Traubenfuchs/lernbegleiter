package at.technikumwien.lernbegleiter.repositories.modules;

import at.technikumwien.lernbegleiter.entities.modules.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface LearningModuleStudentRepository extends JpaRepository<LearningModuleStudentEntity, String> {
}
