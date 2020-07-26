package com.ephoenix.lmsportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages="com.ephoenix.lmsportal")
public class LmsPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsPortalApplication.class, args);
	}

}
