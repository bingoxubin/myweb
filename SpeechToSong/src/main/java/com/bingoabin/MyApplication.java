package com.bingoabin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author xubin03
 * @date 2021/4/12 1:14 上午
 */
@SpringBootApplication
public class MyApplication {
	public static void main(String[] args){
		SpringApplication.run(MyApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate =  new RestTemplate();
		//避免中文乱码
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
}
