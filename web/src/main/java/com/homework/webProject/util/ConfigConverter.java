package com.homework.webProject.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigConverter {
	public ConfigConverter() {
	}

	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@PostConstruct
	public void init() {
		List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter
				.getMessageConverters();
		for (HttpMessageConverter<?> messageConverter : messageConverters) {
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
				ObjectMapper om = m.getObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				om.setDateFormat(dateFormat);
				m.setObjectMapper(om);
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
}
