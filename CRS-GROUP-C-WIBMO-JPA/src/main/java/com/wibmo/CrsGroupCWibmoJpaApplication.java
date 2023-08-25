package com.wibmo;

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
@EnableAutoConfiguration
@Configuration
@EnableWebMvc
@ComponentScan("com.wibmo.*")
@SpringBootApplication
public class CrsGroupCWibmoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupCWibmoJpaApplication.class, args);
	}

}
