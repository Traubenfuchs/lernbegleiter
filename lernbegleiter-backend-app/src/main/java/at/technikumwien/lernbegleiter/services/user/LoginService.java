package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import com.google.common.cache.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.server.*;

import javax.validation.*;
import java.util.*;
import java.util.concurrent.*;

@Transactional
@Validated
@Service
public class LoginService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LoginRepository loginRepository;
  @Autowired
  private PasswordHasher passwordHasher;

  private final LoadingCache<String, UserAuthentication> cache;

  public LoginService() {
    cache = CacheBuilder
      .newBuilder()
      .maximumSize(500)
      .expireAfterWrite(1, TimeUnit.SECONDS)
      .build(new CacheLoader<>() {
        @Override
        public UserAuthentication load(String key) {
          return getAuthenticationForSecretOrThrow(key);
        }
      });
  }

  public UserAuthentication getAuthenticationForSecretOrThrowCached(@NonNull String secret) throws ExecutionException {
    return cache.get(secret);
  }

  public UserAuthentication getAuthenticationForSecretOrThrow(@NonNull String secret) {
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

    checkPassword(loginRequest, userEntity);

    String newSecret = loginRepository.save(new LoginEntity()
      .setSecret(UUID.randomUUID().toString())
      .setUser(userEntity)
    ).getSecret();

    return new LoginResponse()
      .setSecret(newSecret)
      .setRights(new HashSet<>(userEntity.getRights()))
      .setUuid(userEntity.getUuid())
      ;
  }

  private void checkPassword(LoginRequest loginRequest, UserEntity userEntity) {
    if (userEntity == null ||
      !passwordHasher.checkHashedAndSaltedPassword(userEntity.getHashedAndSaltedPassword(), loginRequest.getPassword())
    ) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist or password is incorrect.");
    }
  }
}
