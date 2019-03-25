package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, String> {
    GradeEntity getByName(String name);
}
