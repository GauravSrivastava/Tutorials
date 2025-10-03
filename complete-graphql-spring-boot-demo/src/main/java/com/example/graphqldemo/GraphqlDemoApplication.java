package com.example.graphqldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class
 * Demonstrates GraphQL integration with Spring Boot
 */
@SpringBootApplication
public class GraphqlDemoApplication {

    /**
     * Main method for the application.
     * This method is the entry point for the application and is responsible for
     * starting the Spring Boot application.
     *
     * @param args the command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(GraphqlDemoApplication.class, args);
    }
}