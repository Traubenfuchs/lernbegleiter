package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.repositories.auth.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
