package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface LobRepository extends JpaRepository<LobEntity, String> {
}
