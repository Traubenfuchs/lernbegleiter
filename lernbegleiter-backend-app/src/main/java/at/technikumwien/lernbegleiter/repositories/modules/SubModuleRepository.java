package at.technikumwien.lernbegleiter.repositories.modules;

import at.technikumwien.lernbegleiter.entities.modules.SubModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubModuleRepository extends JpaRepository<SubModuleEntity, String> {
  Set<SubModuleEntity> findByParentUuid(String learningModuleUuid);
}
