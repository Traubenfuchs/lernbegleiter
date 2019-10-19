package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.repositories.auth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.transaction.annotation.*;

import java.time.*;

@Configuration
public class LoginCleaner {
  @Autowired
  private LoginRepository loginRepository;
  @Lazy
  @Autowired
  private LoginCleaner self;

  @Scheduled(fixedDelay = 1000 * 60 * 60)
  public void scheduledClean() {
    self.clean();
  }

  @Transactional
  public void clean() {
    loginRepository.deleteByTsCreationBefore(Instant.now().minusSeconds(5 * 60 * 60 * 24));
  }
}
