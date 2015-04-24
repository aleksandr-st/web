package com.homework.webProject.util;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
@Qualifier(value = "objectMapper")
public class CustomJacksonMapper extends ObjectMapper{
	private static final long serialVersionUID = 6676639462863745282L;
	
	public CustomJacksonMapper() {
		SimpleModule module = new SimpleModule();
		module.addSerializer(DateTime.class, new JodaTimeSerializer());
		registerModule(module);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setSerializationInclusion(Include.NON_NULL);
    }
}