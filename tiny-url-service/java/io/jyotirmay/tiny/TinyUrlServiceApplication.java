package io.jyotirmay.tiny;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class TinyUrlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyUrlServiceApplication.class, args);
	}

	@Bean
	public GroupedOpenApi tinyUserApi() {
	    return GroupedOpenApi.builder().group("Tiny User")
	            .pathsToMatch("/v1/tiny/user") 
	            .build();
	}
	
	@Bean
	public GroupedOpenApi tinyUrlApi() {
	    return GroupedOpenApi.builder().group("Tiny Url")
	            .pathsToMatch("/v1/tiny/url") 
	            .build();
	}
}
