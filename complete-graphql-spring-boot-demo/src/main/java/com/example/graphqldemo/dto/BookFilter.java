package com.example.graphqldemo.dto;

import com.example.graphqldemo.entity.Genre;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookFilter {
    private String title;
    private String author;
    private Genre genre;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocalDateTime publishedAfter;

    // Constructors
    public BookFilter() {}

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }

    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

    public LocalDateTime getPublishedAfter() { return publishedAfter; }
    public void setPublishedAfter(LocalDateTime publishedAfter) { this.publishedAfter = publishedAfter; }
}