package at.technikumwien.xxx.repositories.auth;

import at.technikumwien.xxx.entities.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  boolean existsByEmail(String email);
  UserEntity findByEmail(String email);
}
