package com.wibmo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EntityScan("com.wibmo.model")
@EnableJpaRepositories("com.wibmo.repository")
@EnableWebMvc
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.wibmo.*")
@SpringBootApplication
public class CrsWibmoGroupCStudentMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsWibmoGroupCStudentMicroServiceApplication.class, args);
	}

}
