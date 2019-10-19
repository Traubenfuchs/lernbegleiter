package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, String> {
  Set<ClassEntity> findByGrade(String gradeUuid);
}
