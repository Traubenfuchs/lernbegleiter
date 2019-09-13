package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.components.PasswordHasher;
import at.technikumwien.lernbegleiter.data.UserAuthentication;
import at.technikumwien.lernbegleiter.data.requests.LoginRequest;
import at.technikumwien.lernbegleiter.data.responses.LoginResponse;
import at.technikumwien.lernbegleiter.entities.auth.LoginEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.LoginRepository;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
