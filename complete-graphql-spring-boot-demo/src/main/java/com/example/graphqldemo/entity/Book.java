package com.example.graphqldemo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    // New fields for versioning example
    @ElementCollection
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column
    private Double rating;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    // Constructors
    public Book() {}

    public Book(String title, String isbn, BigDecimal price, LocalDateTime publishedDate, 
                Genre genre, String description, Author author, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
    }

    // Computed methods for GraphQL resolvers
    public String getDisplayPrice() {
        return "$" + price.toString();
    }

    public int getAgeInYears() {
        return (int) ChronoUnit.YEARS.between(publishedDate, LocalDateTime.now());
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}