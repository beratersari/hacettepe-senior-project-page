package seniorproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages={
		"seniorproject", "seniorproject.core", "seniorproject.dataAccess", "seniorproject", "seniorproject.business", "seniorproject.api", "seniorproject.config", "seniorproject", "seniorproject.util", "seniorproject.service", "seniorproject.models", "seniorproject.models.concretes", "seniorproject.models", "seniorproject.models", "seniorproject.models.concretes"})

public class SeniorProjectApplication{
	public static void main(String[] args) {
		SpringApplication.run(SeniorProjectApplication.class, args);
	}

}