package com.cfm.sacc;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SaccApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaccApplication.class, args);
	}
	    
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}