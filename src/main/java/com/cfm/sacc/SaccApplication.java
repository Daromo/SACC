package com.cfm.sacc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SaccApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaccApplication.class, args);
	}
	
    @Bean
    public RestTemplate getresttemplate() {
        return new RestTemplate();
    }
    
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}