package com.example.graphqldemo.config;

import com.example.graphqldemo.scalar.DateTimeScalar;
import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * GraphQL Configuration class
 * Configures custom scalars and other GraphQL-specific settings
 */
@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                // Register custom DateTime scalar
                .scalar(DateTimeScalar.INSTANCE)
                // Register extended BigDecimal scalar for precise decimal handling
                .scalar(ExtendedScalars.GraphQLBigDecimal);
    }
}