package com.ibm.insite.kafkasender.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class IBMJacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		mapper.registerModule(new MyModule());
		return mapper;
	}
}

@SuppressWarnings("serial")
class MyModule extends SimpleModule {
	public MyModule() {
		addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
			@Override
			public String deserialize(JsonParser jp, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return StringUtils.trim(jp.getValueAsString());
			}
		});
	}
}
