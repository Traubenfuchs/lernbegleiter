package at.technikumwien.xxx.repositories;

import at.technikumwien.xxx.entities.SubModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubModuleRepository extends JpaRepository<SubModuleEntity, String> {
}
