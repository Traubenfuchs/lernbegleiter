package at.technikumwien.lernbegleiter.repositories.modules;

import at.technikumwien.lernbegleiter.entities.modules.*;
import org.springframework.data.jpa.repository.*;

public interface LearningModule2LobRepository extends JpaRepository<LearningModule2LobEntity, String> {
  LearningModule2LobEntity findByFkLobUuid(String fkLearningModuleUuid);
}