package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.repositories.auth.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Configuration
public class LoginCleaner {
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private LoginCleaner loginCleaner;

    @Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 60000)
    public void scheduledClean() {
        loginCleaner.clean();
    }

    @Transactional
    public void clean() {
        loginRepository.deleteByTsCreationBefore(Instant.now().minusSeconds(60 * 60 * 24));
    }
}
