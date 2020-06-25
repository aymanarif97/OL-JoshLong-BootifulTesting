package com.ayman.contract.testing.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ConsumerApplication {

	// builds with default security stuff and client-side LBing
	@Bean
	WebClient client(WebClient.Builder builder){
		return builder.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
