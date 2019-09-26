package at.technikumwien.lernbegleiter.repositories.auth;

import at.technikumwien.lernbegleiter.entities.auth.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String> {
  long deleteByTsCreationBefore(Instant cutoff);

  LoginEntity findBySecret(String secret);
}
