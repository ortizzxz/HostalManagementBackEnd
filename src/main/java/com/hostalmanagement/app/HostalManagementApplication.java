package com.hostalmanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HostalManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostalManagementApplication.class, args);
	}

}
