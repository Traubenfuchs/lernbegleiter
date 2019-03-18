package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.UserAuthentication;
import at.technikumwien.lernbegleiter.data.requests.LoginRequest;
import at.technikumwien.lernbegleiter.data.responses.LoginResponse;
import at.technikumwien.lernbegleiter.entities.auth.LoginEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.LoginRepository;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
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

    public UserAuthentication getAuthenticationForSecretOrThrow(@NonNull String secret) {
        LoginEntity le = loginRepository.findBySecret(secret);
        if(le == null) {
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
                .setUser(userEntity)
        ).getSecret();

        return new LoginResponse()
                .setSecret(newSecret)
                .setRights(new HashSet<>(userEntity.getRights()))
                .setUuid(userEntity.getUuid())
                ;
    }

    private void checkSecurityOrThrow(LoginRequest loginRequest, UserEntity userEntity) {
        if(userEntity == null ||
                !passwordHasher.checkHashedAndSaltedPassword(userEntity.getHashedAndSaltedPassword(), loginRequest.getPassword())
        ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist or password is incorrect.");
        }
    }
}
