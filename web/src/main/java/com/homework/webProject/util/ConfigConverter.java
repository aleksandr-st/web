package com.homework.webProject.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class ConfigConverter {
	public ConfigConverter() {
	}

	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	private CustomJacksonMapper objectMapper;

	@PostConstruct
	public void init() {
		List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter
				.getMessageConverters();
		for (HttpMessageConverter<?> messageConverter : messageConverters) {
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
				m.setObjectMapper(objectMapper);
			}
		}
	}

	public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
		return requestMappingHandlerAdapter;
	}

	// this autowire will work cause in xml we have a config <annotation-driven
	// />
	@Autowired
	public void setRequestMappingHandlerAdapter(
			RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
		this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
	}

	public CustomJacksonMapper getObjectMapper() {
		return objectMapper;
	}

	@Autowired
	public void setObjectMapper(CustomJacksonMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
