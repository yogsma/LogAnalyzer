package com.betterjavacode.loganalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
@EnableWebSecurity
public class LoganalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoganalyzerApplication.class, args);
	}

	@Bean
	public RestOperations restOperations(RestTemplateBuilder restTemplateBuilder)
	{
		return restTemplateBuilder.build();
	}

}
