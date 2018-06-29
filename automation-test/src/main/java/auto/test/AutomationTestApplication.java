package auto.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomationTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationTestApplication.class, args);
	}
}
