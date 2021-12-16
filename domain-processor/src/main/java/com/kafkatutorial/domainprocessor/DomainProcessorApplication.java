package com.kafkatutorial.domainprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;

@SpringBootApplication
public class DomainProcessorApplication {

	@Bean
	DefaultJackson2JavaTypeMapper defaultJackson2JavaTypeMapper() {
		DefaultJackson2JavaTypeMapper builder = new DefaultJackson2JavaTypeMapper();
		// Configure the builder to suit your needs
		builder.addTrustedPackages("*");
		return builder;
	}

	public static void main(String[] args) {
		SpringApplication.run(DomainProcessorApplication.class, args);
	}

}
