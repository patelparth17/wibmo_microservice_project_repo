package com.wibmo.application;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan("com.wibmo.model")
@EnableJpaRepositories("com.wibmo.repository")
@EnableWebMvc
@EnableCaching
@EnableSwagger2
@EnableAutoConfiguration
@EnableEurekaClient
@Configuration
@ComponentScan("com.wibmo.*")
@SpringBootApplication
public class CrsGroupCWibmoAuthMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsGroupCWibmoAuthMicroserviceApplication.class, args);
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any()).build().securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));
	}
	private List<SecurityScheme> securitySchemes() {
        return List.of(new BasicAuth("basicAuth"), new ApiKey("Bearer", "Authorization", "header"));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(List.of(basicAuthReference(), bearerAuthReference())).forPaths(PathSelectors.ant("/api/**")).build();
    }
    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }

    private SecurityReference bearerAuthReference() {
        return new SecurityReference("Bearer", new AuthorizationScope[0]);
    }
	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
	   return new InternalResourceViewResolver();
	}
}
