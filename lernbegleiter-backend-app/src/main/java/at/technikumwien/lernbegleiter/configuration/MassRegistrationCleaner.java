package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.entities.massregistration.*;
import at.technikumwien.lernbegleiter.repositories.massregistration.*;
import at.technikumwien.lernbegleiter.services.massregistration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.transaction.annotation.*;

import java.time.*;

@Configuration
public class MassRegistrationCleaner {
  @Autowired
  private MassRegistrationService massRegistrationService;
  @Autowired
  private MassRegistrationRepository massRegistrationRepository;
  @Lazy
  @Autowired
  private MassRegistrationCleaner self;

  @Scheduled(fixedDelay = 1000 * 60 * 60)
  public void scheduledClean() {
    self.clean();
  }

  @Transactional
  public void clean() {
    massRegistrationRepository
      .findByDeletionTimeBefore(Instant.now().minusSeconds(5 * 60 * 60 * 24))
      .stream()
      .map(MassRegistrationEntity::getUuid)
      .forEach(massRegistrationService::delete);
  }
}
