package io.jyotirmay.tiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TinyServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyServiceDiscoveryApplication.class, args);
	}

}
