package de.cwansart.unipoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class UnipollApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnipollApplication.class, args);
	}

}
