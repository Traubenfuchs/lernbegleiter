package at.technikumwien.xxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@SpringBootApplication
@ComponentScan(scopedProxy = TARGET_CLASS)
public class BackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

}
