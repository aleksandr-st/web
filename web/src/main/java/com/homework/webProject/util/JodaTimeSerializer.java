package com.homework.webProject.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JodaTimeSerializer extends JsonSerializer<DateTime> {
	@Override
	public void serialize(DateTime dateTime, JsonGenerator jg, SerializerProvider provider)
		throws IOException, JsonProcessingException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		jg.writeString(sdf.format(dateTime.toDate()));
	}
}
