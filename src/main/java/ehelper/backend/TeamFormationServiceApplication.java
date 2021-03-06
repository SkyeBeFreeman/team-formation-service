package ehelper.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamFormationServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TeamFormationServiceApplication.class);
		app.setWebEnvironment(true);
		app.run(args);
	}
}
