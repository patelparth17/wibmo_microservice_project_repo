package com.wibmo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EntityScan("com.wibmo.model")
@EnableJpaRepositories("com.wibmo.repository")
@EnableAutoConfiguration
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("com.wibmo.*")
@SpringBootApplication
public class CrsGroupCWibmoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupCWibmoJpaApplication.class, args);
	}

	@Bean
    public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    @Bean
      public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
     }
}
