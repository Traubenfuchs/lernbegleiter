package at.technikumwien.lernbegleiter;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.transaction.annotation.*;

import static org.springframework.context.annotation.ScopedProxyMode.*;

@SpringBootApplication
@ComponentScan(scopedProxy = TARGET_CLASS)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
public class LernbegleiterApplication {
// normal answers
  // -import moodle.xml

  public static void main(String[] args) {
    SpringApplication.run(LernbegleiterApplication.class, args);
  }
}
