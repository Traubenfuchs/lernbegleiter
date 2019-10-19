package at.technikumwien.lernbegleiter.repositories.auth;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  boolean existsByEmail(String email);

  UserEntity findByEmail(String email);

  @Query("SELECT u FROM UserEntity u WHERE ?1 member of u.rights")
  Set<UserEntity> findByRightsContains(String right);

  default UserEntity getCurrentUser() {
    return getOne(AuthHelper.getCurrentUserUUIDOrThrow());
  }
}
