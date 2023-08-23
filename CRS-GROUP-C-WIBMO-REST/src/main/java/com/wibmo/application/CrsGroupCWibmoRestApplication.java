package com.wibmo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration

@Configuration

@ComponentScan("com.wibmo.*")

@EnableWebMvc
public class CrsGroupCWibmoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupCWibmoRestApplication.class, args);
	}

}
