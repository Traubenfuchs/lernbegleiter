package at.technikumwien.xxx.services;

import at.technikumwien.xxx.data.RegistrationRequest;
import at.technikumwien.xxx.entities.auth.UserEntity;
import at.technikumwien.xxx.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Service
public class RegistrationService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordHasher passwordHasher;

  public String register(@NonNull @Valid RegistrationRequest request) {
    String email = request.getEmail();

    if (userRepository.existsByEmail(email)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email<" + email + "> already in use.");
    }

    byte[] hashedAndSaltedPassword = passwordHasher.hashAndSalt(request.getPassword());

    UserEntity userEntity = new UserEntity()
        .setEmail(email)
        .setHashedAndSaltedPassword(hashedAndSaltedPassword)
        .setBirthday(request.getBirthday())
        .setFirstName(request.getFirstName())
        .setFamilyName(request.getFamilyName())
        .generateUuid();

    userEntity = userRepository.save(userEntity);

    return userEntity.getUuid();
  }
}
