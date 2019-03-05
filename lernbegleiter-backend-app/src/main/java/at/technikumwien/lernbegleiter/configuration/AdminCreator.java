package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.services.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class AdminCreator {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordHasher passwordHasher;

  @PostConstruct
  public void postConstruct() {
    if(!userRepository.existsByEmail("admin")) {
      userRepository.save(new UserEntity()
          .setRights(new HashSet<>(Arrays.asList("ADMIN", "TEACHER")))
          .setBirthday(LocalDate.now())
          .setEmail("admin")
          .setFamilyName("admin")
          .setFirstName("admin")
          .setHashedAndSaltedPassword(passwordHasher.hashAndSalt("admin"))
      );
    }
  }
}
