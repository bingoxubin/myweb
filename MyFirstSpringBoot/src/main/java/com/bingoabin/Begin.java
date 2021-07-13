package com.bingoabin;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author xubin03
 * @date 2021/4/11 10:13 下午
 */
@SpringBootApplication
public class Begin {
	public static void main(String[] args) {
		SpringApplication.run(Begin.class, args);
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName){
		return registry -> registry.config().commonTags("application", applicationName);
	}
}
