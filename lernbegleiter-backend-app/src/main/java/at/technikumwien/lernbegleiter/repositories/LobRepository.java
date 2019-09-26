package at.technikumwien.lernbegleiter.repositories;

import at.technikumwien.lernbegleiter.entities.LobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobRepository extends JpaRepository<LobEntity, String> {
}
