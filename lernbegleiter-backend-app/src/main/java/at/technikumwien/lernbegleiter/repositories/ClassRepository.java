package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, String> {
    Set<ClassEntity> findByGrade(String gradeUuid);
}
