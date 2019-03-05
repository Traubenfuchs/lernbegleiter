package at.technikumwien.xxx.repositories;

import at.technikumwien.xxx.entities.ClassEntity;
import at.technikumwien.xxx.entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository  extends JpaRepository<GradeEntity, String> {
}
