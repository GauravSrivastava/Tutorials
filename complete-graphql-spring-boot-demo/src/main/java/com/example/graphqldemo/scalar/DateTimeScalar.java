package com.example.graphqldemo.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Custom GraphQL Scalar for LocalDateTime
 * Handles serialization and deserialization of LocalDateTime objects
 */
public class DateTimeScalar {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("A custom scalar that represents a LocalDateTime")
            .coercing(new Coercing<LocalDateTime, String>() {

                @Override
                public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale) 
                        throws CoercingSerializeException {
                    if (dataFetcherResult instanceof LocalDateTime) {
                        return ((LocalDateTime) dataFetcherResult).format(FORMATTER);
                    } else if (dataFetcherResult instanceof String) {
                        return (String) dataFetcherResult;
                    } else {
                        throw new CoercingSerializeException("Expected LocalDateTime but was " + 
                                dataFetcherResult.getClass());
                    }
                }

                @Override
                public LocalDateTime parseValue(Object input, GraphQLContext graphQLContext, Locale locale) 
                        throws CoercingParseValueException {
                    if (input instanceof String) {
                        try {
                            return LocalDateTime.parse((String) input, FORMATTER);
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException("Invalid DateTime format: " + input, e);
                        }
                    } else if (input instanceof LocalDateTime) {
                        return (LocalDateTime) input;
                    } else {
                        throw new CoercingParseValueException("Expected String or LocalDateTime but was " + 
                                input.getClass());
                    }
                }

                @Override
                public LocalDateTime parseLiteral(Value<?> input, CoercedVariables variables, 
                        GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
                    if (input instanceof StringValue) {
                        try {
                            return LocalDateTime.parse(((StringValue) input).getValue(), FORMATTER);
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException("Invalid DateTime format: " + 
                                    ((StringValue) input).getValue(), e);
                        }
                    } else {
                        throw new CoercingParseLiteralException("Expected StringValue but was " + 
                                input.getClass());
                    }
                }
            })
            .build();
}