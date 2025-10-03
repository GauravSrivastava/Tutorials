package com.example.graphqldemo.repository;

import com.example.graphqldemo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByEmail(String email);

    List<Author> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    @Query("SELECT a FROM Author a WHERE " +
           "LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> findByFullNameContaining(@Param("name") String name);

    // Search authors
    @Query("SELECT a FROM Author a WHERE " +
           "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Author> searchAuthors(@Param("query") String query);
}