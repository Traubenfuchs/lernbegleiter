package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import at.technikumwien.lernbegleiter.components.PasswordHasher;
import at.technikumwien.lernbegleiter.data.requests.UserUpdateDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
  public boolean deleteByUuid(@NonNull String userUuid) {
    if (!userRepository.existsById(userUuid)) {
      return false;
    }
    userRepository.deleteById(userUuid);
    return true;
  }

  public void update(@NonNull String userUuid, @Valid @NonNull UserUpdateDto uur) {
    authHelper.isAdminOrTeacherOrCurrentUserUuidOrThrow(userUuid);

    UserEntity ue = userRepository.getOne(userUuid);

    if (!StringUtils.isEmpty(uur.getPassword())) {
      ue.setHashedAndSaltedPassword(passwordHasher.hashAndSalt(uur.getPassword()));
    }
    if (uur.getEmail() != null) {
      ue.setEmail(uur.getEmail());
    }
    if (uur.getBirthday() != null) {
      ue.setBirthday(uur.getBirthday());
    }
    if (uur.getFamilyName() != null) {
      ue.setFamilyName(uur.getFamilyName());
    }
    if (uur.getFirstName() != null) {
      ue.setFirstName(uur.getFirstName());
    }
  }

  private void enforceAdminOrTeacher() {
    authHelper.hasAnyRoleOrThrow("TEACHER", "ADMIN");
  }

  public UserUpdateDto get(@NonNull String userUuid) {
    UserEntity ue = userRepository.getOne(userUuid);

    return new UserUpdateDto()
        .setBirthday(ue.getBirthday())
        .setEmail(ue.getEmail())
        .setFamilyName(ue.getFamilyName())
        .setFirstName(ue.getFirstName());
  }
}
