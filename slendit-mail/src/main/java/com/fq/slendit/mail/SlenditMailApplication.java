package com.fq.slendit.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SlenditMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlenditMailApplication.class, args);
		System.out.println("Email application started");
	}

}
