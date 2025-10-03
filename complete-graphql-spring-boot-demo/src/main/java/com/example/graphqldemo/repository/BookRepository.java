package com.example.graphqldemo.repository;

import com.example.graphqldemo.entity.Book;
import com.example.graphqldemo.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Custom query methods for filtering
    List<Book> findByAuthorId(Long authorId);

    List<Book> findByGenre(Genre genre);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:genre IS NULL OR b.genre = :genre) AND " +
           "(:minPrice IS NULL OR b.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR b.price <= :maxPrice) AND " +
           "(:publishedAfter IS NULL OR b.publishedDate >= :publishedAfter)")
    Page<Book> findBooksWithFilter(
            @Param("title") String title,
            @Param("genre") Genre genre,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("publishedAfter") LocalDateTime publishedAfter,
            Pageable pageable
    );

    // Search across multiple fields
    @Query("SELECT b FROM Book b JOIN b.author a WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooks(@Param("query") String query);
}