package co.wordly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorldyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldyApplication.class, args);
	}

}
