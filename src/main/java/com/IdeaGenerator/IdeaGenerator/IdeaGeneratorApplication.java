package com.IdeaGenerator.IdeaGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.IdeaGenerator.IdeaGenerator.Repository")
@SpringBootApplication
public class IdeaGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeaGeneratorApplication.class, args);
	}
}
