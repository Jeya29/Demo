package com.org.candidate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan({"com.org.controller","com.org.service","com.org.aop","com.org.exception"})
@EntityScan("com.org.entity")
@EnableJpaRepositories("com.org.repository")

public class CandidateApplication{

	public static void main(String[] args) {
		SpringApplication.run(CandidateApplication.class, args);
	}

}
