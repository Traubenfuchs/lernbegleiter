package at.technikumwien.lernbegleiter.repositories.auth;

import at.technikumwien.lernbegleiter.entities.auth.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.time.*;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String> {
  long deleteByTsCreationBefore(Instant cutoff);

  LoginEntity findBySecret(String secret);
}
