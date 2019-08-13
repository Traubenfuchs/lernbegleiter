package at.technikumwien.lernbegleiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@SpringBootApplication
@ComponentScan(scopedProxy = TARGET_CLASS)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
public class LernbegleiterApplication {

    public static void main(String[] args) {
        System.out.println(System.getenv("DB_HOST_AND_PORT"));
        SpringApplication.run(LernbegleiterApplication.class, args);
    }
}
