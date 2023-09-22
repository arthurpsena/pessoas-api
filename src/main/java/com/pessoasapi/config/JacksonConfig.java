package com.pessoasapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper jsonMapper() {
		final ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.registerModule(new JavaTimeModule());
		jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return jsonMapper;
	}

}
