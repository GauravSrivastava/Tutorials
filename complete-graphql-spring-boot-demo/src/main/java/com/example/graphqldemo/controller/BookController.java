package com.example.graphqldemo.controller;

import com.example.graphqldemo.dto.BookFilter;
import com.example.graphqldemo.dto.BookInput;
import com.example.graphqldemo.entity.Author;
import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.entity.Publisher;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import com.example.graphqldemo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GraphQL Controller for Book operations
 * Demonstrates Query Mapping, Variables, and Operations
 */
@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    // Query Operations with Variables and Filtering
    @QueryMapping
    public List<Book> books(@Argument Integer limit, 
                           @Argument Integer offset, 
                           @Argument BookFilter filter) {
        // Default values
        if (limit == null) limit = 10;
        if (offset == null) offset = 0;

        Pageable pageable = PageRequest.of(offset / limit, limit);

        if (filter != null) {
            return bookRepository.findBooksWithFilter(
                    filter.getTitle(),
                    filter.getGenre(),
                    filter.getMinPrice(),
                    filter.getMaxPrice(),
                    filter.getPublishedAfter(),
                    pageable
            ).getContent();
        } else {
            return bookRepository.findAll(pageable).getContent();
        }
    }

    @QueryMapping
    public Optional<Book> bookById(@Argument String id) {
        return bookRepository.findById(Long.parseLong(id));
    }

    @QueryMapping
    public List<Book> booksByAuthor(@Argument String authorId) {
        return bookRepository.findByAuthorId(Long.parseLong(authorId));
    }

    // Deprecated query for versioning demonstration
    @QueryMapping
    @Deprecated
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    // Search operation demonstrating Union types
    @QueryMapping
    public List<Object> searchBooks(@Argument String query) {
        List<Object> results = new ArrayList<>();

        // Search books
        results.addAll(bookRepository.searchBooks(query));

        // Search authors
        results.addAll(authorRepository.searchAuthors(query));

        // Search publishers
        results.addAll(publisherRepository.searchPublishers(query));

        return results;
    }

    // Mutation Operations
    @MutationMapping
    public BookPayload createBook(@Argument BookInput input) {
        try {
            // Validate author and publisher exist
            Optional<Author> author = authorRepository.findById(input.getAuthorId());
            Optional<Publisher> publisher = publisherRepository.findById(input.getPublisherId());

            if (author.isEmpty()) {
                return new BookPayload(null, 
                    List.of(new ErrorDetails("authorId", "Author not found", "AUTHOR_NOT_FOUND")), 
                    false);
            }

            if (publisher.isEmpty()) {
                return new BookPayload(null, 
                    List.of(new ErrorDetails("publisherId", "Publisher not found", "PUBLISHER_NOT_FOUND")), 
                    false);
            }

            Book book = new Book();
            book.setTitle(input.getTitle());
            book.setIsbn(input.getIsbn());
            book.setPrice(input.getPrice());
            book.setPublishedDate(input.getPublishedDate());
            book.setGenre(input.getGenre());
            book.setDescription(input.getDescription());
            book.setAuthor(author.get());
            book.setPublisher(publisher.get());

            Book savedBook = bookRepository.save(book);
            return new BookPayload(savedBook, Collections.emptyList(), true);

        } catch (Exception e) {
            return new BookPayload(null, 
                List.of(new ErrorDetails("general", e.getMessage(), "CREATION_ERROR")), 
                false);
        }
    }

    @MutationMapping
    public BookPayload updateBook(@Argument String id, @Argument BookInput input) {
        try {
            Optional<Book> existingBook = bookRepository.findById(Long.parseLong(id));

            if (existingBook.isEmpty()) {
                return new BookPayload(null, 
                    List.of(new ErrorDetails("id", "Book not found", "BOOK_NOT_FOUND")), 
                    false);
            }

            Book book = existingBook.get();
            book.setTitle(input.getTitle());
            book.setIsbn(input.getIsbn());
            book.setPrice(input.getPrice());
            book.setPublishedDate(input.getPublishedDate());
            book.setGenre(input.getGenre());
            book.setDescription(input.getDescription());

            // Update author and publisher if provided
            if (input.getAuthorId() != null) {
                Optional<Author> author = authorRepository.findById(input.getAuthorId());
                if (author.isPresent()) {
                    book.setAuthor(author.get());
                }
            }

            if (input.getPublisherId() != null) {
                Optional<Publisher> publisher = publisherRepository.findById(input.getPublisherId());
                if (publisher.isPresent()) {
                    book.setPublisher(publisher.get());
                }
            }

            Book updatedBook = bookRepository.save(book);
            return new BookPayload(updatedBook, Collections.emptyList(), true);

        } catch (Exception e) {
            return new BookPayload(null, 
                List.of(new ErrorDetails("general", e.getMessage(), "UPDATE_ERROR")), 
                false);
        }
    }

    @MutationMapping
    public DeletePayload deleteBook(@Argument String id) {
        try {
            Long bookId = Long.parseLong(id);
            if (!bookRepository.existsById(bookId)) {
                return new DeletePayload(null, false, 
                    List.of(new ErrorDetails("id", "Book not found", "BOOK_NOT_FOUND")));
            }

            bookRepository.deleteById(bookId);
            return new DeletePayload(id, true, Collections.emptyList());

        } catch (Exception e) {
            return new DeletePayload(null, false, 
                List.of(new ErrorDetails("general", e.getMessage(), "DELETE_ERROR")));
        }
    }

    @MutationMapping
    public List<BookPayload> createBooks(@Argument List<BookInput> input) {
        return input.stream()
                .map(this::createBookInternal)
                .collect(Collectors.toList());
    }

    private BookPayload createBookInternal(BookInput input) {
        return createBook(input);
    }

    // Payload classes for mutations
    public static class BookPayload {
        private final Book book;
        private final List<ErrorDetails> errors;
        private final boolean success;

        public BookPayload(Book book, List<ErrorDetails> errors, boolean success) {
            this.book = book;
            this.errors = errors;
            this.success = success;
        }

        public Book getBook() { return book; }
        public List<ErrorDetails> getErrors() { return errors; }
        public boolean isSuccess() { return success; }
    }

    public static class DeletePayload {
        private final String id;
        private final boolean success;
        private final List<ErrorDetails> errors;

        public DeletePayload(String id, boolean success, List<ErrorDetails> errors) {
            this.id = id;
            this.success = success;
            this.errors = errors;
        }

        public String getId() { return id; }
        public boolean isSuccess() { return success; }
        public List<ErrorDetails> getErrors() { return errors; }
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