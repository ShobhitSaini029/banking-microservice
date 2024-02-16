package com.nagarro.service.registory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // To make this app a service registry.
public class BankingServiceRegistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingServiceRegistoryApplication.class, args);
	}

}
