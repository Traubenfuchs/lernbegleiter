package at.technikumwien.lernbegleiter.test.helper;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.services.user.*;
import lombok.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Component
@AllArgsConstructor
public class TestAuthHelper {
  private final LoginService loginService;
  private final UserRepository userRepository;
  private final RegistrationService registrationService;
  private final LoginRepository loginRepository;
  private final ThreadLocal<String> userAuth = ThreadLocal.withInitial(() -> null);

  public void authAsAdmin() {
    UserEntity userEntity = userRepository.findByEmail("admin");
    UserAuthentication ua = loginService.userToAuthentication(userEntity);
    SecurityContextHolder.getContext().setAuthentication(ua);
  }

  @Transactional
  public void authAsUser(String username) {
    String newUserUuid = registrationService.register(
      new RegistrationRequest()
        .setBirthday(LocalDate.now())
        .setEmail(username)
        .setPassword(username)
        .setFamilyName(username)
        .setFirstName(username));

    UserEntity userEntity = userRepository.getOne(newUserUuid);
    UserAuthentication ua = loginService.userToAuthentication(userEntity);
    SecurityContextHolder.getContext().setAuthentication(ua);

    String newSecret = loginRepository.save(new LoginEntity()
      .setSecret(UUID.randomUUID().toString())
      .setUser(userEntity)
    ).getSecret();

    userAuth.set(newSecret);
  }

  public String getCurrentUsersSecret() {
    String result = userAuth.get();
    if (result == null) {
      throw new RuntimeException("No User logged in!");
    }
    return result;
  }
}