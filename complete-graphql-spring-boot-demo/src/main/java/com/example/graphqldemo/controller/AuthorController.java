package com.example.graphqldemo.controller;

import com.example.graphqldemo.dto.AuthorInput;
import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.entity.Publisher;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * GraphQL Controller for Author operations
 */
@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @QueryMapping
    public List<Author> authors(@Argument Integer limit) {
        if (limit == null) limit = 10;
        return authorRepository.findAll().stream().limit(limit).toList();
    }

    @QueryMapping
    public Optional<Author> authorById(@Argument String id) {
        return authorRepository.findById(Long.parseLong(id));
    }

    @QueryMapping
    public List<Publisher> publishers() {
        return publisherRepository.findAll();
    }

    @QueryMapping
    public Optional<Publisher> publisherById(@Argument String id) {
        return publisherRepository.findById(Long.parseLong(id));
    }

    @MutationMapping
    public AuthorPayload createAuthor(@Argument AuthorInput input) {
        try {
            // Check if author with email already exists
            Optional<Author> existingAuthor = authorRepository.findByEmail(input.getEmail());
            if (existingAuthor.isPresent()) {
                return new AuthorPayload(null, 
                    List.of(new ErrorDetails("email", "Email already exists", "DUPLICATE_EMAIL")), 
                    false);
            }

            Author author = new Author();
            author.setFirstName(input.getFirstName());
            author.setLastName(input.getLastName());
            author.setEmail(input.getEmail());
            author.setBirthDate(input.getBirthDate());
            author.setBiography(input.getBiography());

            Author savedAuthor = authorRepository.save(author);
            return new AuthorPayload(savedAuthor, Collections.emptyList(), true);

        } catch (Exception e) {
            return new AuthorPayload(null, 
                List.of(new ErrorDetails("general", e.getMessage(), "CREATION_ERROR")), 
                false);
        }
    }

    @MutationMapping
    public AuthorPayload updateAuthor(@Argument String id, @Argument AuthorInput input) {
        try {
            Optional<Author> existingAuthor = authorRepository.findById(Long.parseLong(id));

            if (existingAuthor.isEmpty()) {
                return new AuthorPayload(null, 
                    List.of(new ErrorDetails("id", "Author not found", "AUTHOR_NOT_FOUND")), 
                    false);
            }

            Author author = existingAuthor.get();
            author.setFirstName(input.getFirstName());
            author.setLastName(input.getLastName());
            author.setEmail(input.getEmail());
            author.setBirthDate(input.getBirthDate());
            author.setBiography(input.getBiography());

            Author updatedAuthor = authorRepository.save(author);
            return new AuthorPayload(updatedAuthor, Collections.emptyList(), true);

        } catch (Exception e) {
            return new AuthorPayload(null, 
                List.of(new ErrorDetails("general", e.getMessage(), "UPDATE_ERROR")), 
                false);
        }
    }

    // Payload and Error classes
    public static class AuthorPayload {
        private final Author author;
        private final List<ErrorDetails> errors;
        private final boolean success;

        public AuthorPayload(Author author, List<ErrorDetails> errors, boolean success) {
            this.author = author;
            this.errors = errors;
            this.success = success;
        }

        public Author getAuthor() { return author; }
        public List<ErrorDetails> getErrors() { return errors; }
        public boolean isSuccess() { return success; }
    }

    public static class ErrorDetails {
        private final String field;
        private final String message;
        private final String code;

        public ErrorDetails(String field, String message, String code) {
            this.field = field;
            this.message = message;
            this.code = code;
        }

        public String getField() { return field; }
        public String getMessage() { return message; }
        public String getCode() { return code; }
    }
}