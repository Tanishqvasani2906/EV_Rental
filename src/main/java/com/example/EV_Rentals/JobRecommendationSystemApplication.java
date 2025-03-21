package com.example.EV_Rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobRecommendationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobRecommendationSystemApplication.class, args);
	}

}
