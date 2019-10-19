package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, String> {
  GradeEntity findByName(String name);
}
