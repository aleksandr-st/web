package com.homework.webProject.util;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class ConfigConverterTest {
	private static ConfigConverter configConverter;

	@Before
	public void setUp() throws Exception {
		configConverter = new ConfigConverter();
	}

	@Test
	public void testInit() {
		RequestMappingHandlerAdapter rmha = new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		messageConverters.add(new FormHttpMessageConverter());
		rmha.setMessageConverters(messageConverters);
		configConverter.setRequestMappingHandlerAdapter(rmha);
		configConverter.init();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		rmha = configConverter.getRequestMappingHandlerAdapter();
		assertNotNull(rmha);
		MappingJackson2HttpMessageConverter updatedMessageConverter = (MappingJackson2HttpMessageConverter)rmha.getMessageConverters().get(0);
		assertNotNull(updatedMessageConverter);
		assertEquals(dateFormat, updatedMessageConverter.getObjectMapper().getDateFormat());
	}

	@Test
	public void testSetGetRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter rmha = new RequestMappingHandlerAdapter();
		configConverter.setRequestMappingHandlerAdapter(rmha);
		assertNotNull(configConverter.getRequestMappingHandlerAdapter());
		assertEquals(rmha, configConverter.getRequestMappingHandlerAdapter());
	}

}
