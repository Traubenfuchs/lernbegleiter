package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.server.*;

import javax.validation.*;

@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Validated
@Service
public class RegistrationService {
  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  /**
   * @param request
   * @return new users UUID
   */
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
