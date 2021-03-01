package edu.uark.registerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegisterApplication {

	public static void main(final String[] args) {
		System.setProperty("server.port", "6500");
		SpringApplication.run(RegisterApplication.class, args);
	}

}
