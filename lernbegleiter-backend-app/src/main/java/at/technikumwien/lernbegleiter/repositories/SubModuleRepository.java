package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.SubModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubModuleRepository extends JpaRepository<SubModuleEntity, String> {
}
