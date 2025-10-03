package com.example.graphqldemo.resolver;

import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.entity.Publisher;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Component;

/**
 * GraphQL Resolvers (DataFetchers) for Book entity
 * Demonstrates how to resolve nested fields and relationships
 */
@Component
public class BookResolver {

    /**
     * Resolver for Book.author field
     * This is called when the author field is requested in a Book query
     * Demonstrates N+1 problem handling - in production, use DataLoader
     */
    @SchemaMapping
    public Author author(Book book) {
        // In real applications, consider using DataLoader to avoid N+1 queries
        return book.getAuthor();
    }

    /**
     * Resolver for Book.publisher field
     * This demonstrates field-level data fetching
     */
    @SchemaMapping
    public Publisher publisher(Book book) {
        return book.getPublisher();
    }

    /**
     * Resolver for computed fields
     * These fields are calculated dynamically and not stored in database
     */
    @SchemaMapping
    public String displayPrice(Book book) {
        return book.getDisplayPrice();
    }

    @SchemaMapping
    public Integer ageInYears(Book book) {
        return book.getAgeInYears();
    }

    /**
     * Resolver for new fields added in versioning
     * These demonstrate backward-compatible schema evolution
     */
    @SchemaMapping
    public java.util.List<String> tags(Book book) {
        return book.getTags() != null ? book.getTags() : java.util.Collections.emptyList();
    }

    @SchemaMapping
    public Double rating(Book book) {
        return book.getRating();
    }
}