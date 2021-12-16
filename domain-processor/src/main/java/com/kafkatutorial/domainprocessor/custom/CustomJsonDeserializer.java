package com.kafkatutorial.domainprocessor.custom;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
	public CustomJsonDeserializer() {
		super();
//		this.addTrustedPackages("com.kafkatutorial.domaincrawler.model.Domain");
		this.addTrustedPackages("*");
	}
}