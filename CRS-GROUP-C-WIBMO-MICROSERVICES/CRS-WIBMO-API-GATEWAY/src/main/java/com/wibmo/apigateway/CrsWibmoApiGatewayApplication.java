package com.wibmo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.wibmo.routeconfiguration.SpringCloudConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@Import({SpringCloudConfiguration.class})
@EnableEurekaClient
public class CrsWibmoApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsWibmoApiGatewayApplication.class, args);
	}

}
