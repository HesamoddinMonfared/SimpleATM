package com.sampledomain.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class AtmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sampledomain.atm")).build();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() { // Essential for Eureka client {Prevent HostNotFoundException}
		return new RestTemplate();
	}

}
