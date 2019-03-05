package at.technikumwien.xxx.services;

import at.technikumwien.xxx.data.LoginRequest;
import at.technikumwien.xxx.data.LoginResponse;
import at.technikumwien.xxx.data.UserAuthentication;
import at.technikumwien.xxx.entities.auth.LoginEntity;
import at.technikumwien.xxx.entities.auth.UserEntity;
import at.technikumwien.xxx.repositories.auth.LoginRepository;
import at.technikumwien.xxx.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.UUID;

@Validated
@Service
public class LoginService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LoginRepository loginRepository;
  @Autowired
  private PasswordHasher passwordHasher;

  public UserAuthentication getAuthenticationForSecretOrThrow(String secret) {
    LoginEntity le = loginRepository.findBySecret(secret);
    if (le == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid secret. Please login again.");
    }
    return userToAuthentication(le.getUser());
  }

  private UserAuthentication userToAuthentication(UserEntity userEntity) {
    return new UserAuthentication()
        .setUuid(userEntity.getUuid())
        .setRights(new HashSet<>(userEntity.getRights()));
  }

  /**
   * Returns the secret the user can use to authenticate himself.
   *
   * @param loginRequest
   * @return
   */
  public LoginResponse login(@NonNull @Valid LoginRequest loginRequest) {
    UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail());

    checkSecurityOrThrow(loginRequest, userEntity);

    String newSecret = loginRepository.save(new LoginEntity()
        .setSecret(UUID.randomUUID().toString())
        .setUser(null)
    ).getSecret();

    return new LoginResponse()
        .setSecret(newSecret)
        .setRights(new HashSet<>(userEntity.getRights()))
        .setUuid(userEntity.getUuid())
        ;
  }

  private void checkSecurityOrThrow(LoginRequest loginRequest, UserEntity userEntity) {
    if (userEntity == null ||
        !passwordHasher.checkHashedAndSaltedPassword(userEntity.getHashedAndSaltedPassword(), loginRequest.getPassword())
    ) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist or password is incorrect.");
    }
  }
}
