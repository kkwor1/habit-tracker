package com.university.habittracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration for Swagger/OpenAPI documentation.
 * Provides comprehensive API documentation accessible at /swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Habit Tracker API")
                        .version("1.0.0")
                        .description("""
                    REST API for managing tasks and habits with daily rollover logic.
                    
                    ## Features
                    - Create, read, update, and delete tasks
                    - Track daily progress with accumulative logic
                    - Mark tasks as completed for specific dates
                    - View completion statistics and history
                    - Filter and sort tasks by priority and date
                    
                    ## Business Logic
                    - Tasks repeat daily until their end date
                    - Missed days accumulate to the next day's target
                    - Completed tasks reset for the next day
                    - Daily rollover processing updates all active tasks
                    
                    ## Technical Details
                    - Built with Spring Boot 3.2.0 and Java 17
                    - Uses H2 in-memory database for development
                    - Implements Strategy Pattern for rollover logic
                    - Follows RESTful principles with proper status codes
                    """)
                        .contact(new Contact()
                                .name("University Project Team")
                                .email("support@habittracker.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ));
    }
}
