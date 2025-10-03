package com.example.graphqldemo.dto;

import com.example.graphqldemo.entity.Genre;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookInput {
    private String title;
    private String isbn;
    private BigDecimal price;
    private LocalDateTime publishedDate;
    private Long authorId;
    private Long publisherId;
    private Genre genre;
    private String description;

    // Constructors
    public BookInput() {}

    public BookInput(String title, String isbn, BigDecimal price, LocalDateTime publishedDate,
                     Long authorId, Long publisherId, Genre genre, String description) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.genre = genre;
        this.description = description;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}