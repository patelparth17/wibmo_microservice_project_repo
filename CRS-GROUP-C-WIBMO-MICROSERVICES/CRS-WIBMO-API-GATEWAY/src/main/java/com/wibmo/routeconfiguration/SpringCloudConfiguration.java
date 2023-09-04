/**
 * 
 */
package com.wibmo.routeconfiguration;

/**
 * @author parth.patel
 */
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 

@Configuration
public class SpringCloudConfiguration {

	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        System.out.println("n router");
        return builder.routes()
                 .route(r -> r.path("/api/student/**")
                         .uri("http://localhost:8081/")    // Student Microservice port
                 )
                 .route(r -> r.path("/api/professor/**")
                		 .uri("http://localhost:8082/")   // Professor Microservice port
                 )
                 .route(r -> r.path("/api/admin/**")
                		 .uri("http://localhost:8083/")   // Admin Microservice port
                 )
                 .route(r -> r.path("/api/auth/**")
                		 .uri("http://localhost:8084/")   // Authorization Microservice port
                 )
                 .build();
    }
}

