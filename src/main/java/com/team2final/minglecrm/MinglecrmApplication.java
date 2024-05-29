package com.team2final.minglecrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableJpaAuditing
@SpringBootApplication
@EnableMethodSecurity
public class MinglecrmApplication {
	public static void main(String[] args) {
		SpringApplication.run(MinglecrmApplication.class, args);
	}
}
