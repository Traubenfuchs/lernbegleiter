package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.UserUpdateRequest;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Transactional
@Validated
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordHasher passwordHasher;
  @Autowired
  private AuthHelper authHelper;

  @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
  public boolean deleteByUuid(@NonNull String uuid) {
    if (!userRepository.existsById(uuid)) {
      return false;
    }
    userRepository.deleteById(uuid);
    return true;
  }

  public void update(@NonNull String uuid, @Valid @NonNull UserUpdateRequest uur) {
    UserEntity ue = userRepository.getOne(uuid);

    if (authHelper.hasRole("STUDENT")) {
      // Students can only change themselves
      authHelper.currentUserHasUuidOrThrow(uuid);
    }

    if (uur.getPassword() != null) {
      enforceAtLeastStudent();
      ue.setHashedAndSaltedPassword(passwordHasher.hashAndSalt(uur.getPassword()));
    }
    if (uur.getEmail() != null) {
      enforceAtLeastStudent();
      ue.setEmail(uur.getEmail());
    }

    if (uur.getBirthday() != null) {
      enforceAdminOrTeacher();
      ue.setBirthday(uur.getBirthday());
    }
    if (uur.getFamilyName() != null) {
      enforceAdminOrTeacher();
      ue.setFamilyName(uur.getFamilyName());
    }
    if (uur.getFirstName() != null) {
      enforceAdminOrTeacher();
      ue.setFirstName(uur.getFirstName());
    }
  }

  private void enforceAtLeastStudent() {
    authHelper.hasAnyRoleOrThrow("STUDENT", "TEACHER", "ADMIN");
  }

  private void enforceAdminOrTeacher() {
    authHelper.hasAnyRoleOrThrow("TEACHER", "ADMIN");
  }
}
