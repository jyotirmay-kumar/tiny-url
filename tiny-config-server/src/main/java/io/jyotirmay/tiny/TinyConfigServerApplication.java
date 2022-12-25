package io.jyotirmay.tiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TinyConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyConfigServerApplication.class, args);
	}

}
