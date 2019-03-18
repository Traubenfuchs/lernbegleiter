package at.technikumwien.lernbegleiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@SpringBootApplication
@ComponentScan(scopedProxy = TARGET_CLASS)
@EnableTransactionManagement(proxyTargetClass = true)
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
