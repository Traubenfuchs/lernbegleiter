package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import javax.annotation.*;
import java.time.*;
import java.util.*;

@Configuration
public class AdminCreator {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordHasher passwordHasher;

  @PostConstruct
  public void postConstruct() {
    if (userRepository.existsByEmail("admin")) {
      return;
    }

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
