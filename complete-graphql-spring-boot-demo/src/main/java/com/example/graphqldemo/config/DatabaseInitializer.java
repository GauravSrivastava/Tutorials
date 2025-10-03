package com.example.graphqldemo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Database initializer to ensure tables are created with correct schema
 */
@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            System.out.println("=== Initializing Database Schema ===");
            
            // Drop existing tables
            jdbcTemplate.execute("DROP TABLE IF EXISTS book_tags CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS books CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS publishers CASCADE");
            jdbcTemplate.execute("DROP TABLE IF EXISTS authors CASCADE");
            
            System.out.println("=== Dropped existing tables ===");
            
            // Create Authors table
            jdbcTemplate.execute(
                "CREATE TABLE authors (" +
                "    id BIGSERIAL PRIMARY KEY," +
                "    first_name VARCHAR(100) NOT NULL," +
                "    last_name VARCHAR(100) NOT NULL," +
                "    email VARCHAR(255) UNIQUE NOT NULL," +
                "    birth_date TIMESTAMP," +
                "    biography TEXT" +
                ")"
            );
            
            // Create Publishers table
            jdbcTemplate.execute(
                "CREATE TABLE publishers (" +
                "    id BIGSERIAL PRIMARY KEY," +
                "    name VARCHAR(255) NOT NULL," +
                "    address VARCHAR(500) NOT NULL," +
                "    website VARCHAR(255)" +
                ")"
            );
            
            // Create Books table
            jdbcTemplate.execute(
                "CREATE TABLE books (" +
                "    id BIGSERIAL PRIMARY KEY," +
                "    title VARCHAR(255) NOT NULL," +
                "    isbn VARCHAR(20) UNIQUE NOT NULL," +
                "    price DECIMAL(10,2) NOT NULL," +
                "    published_date TIMESTAMP NOT NULL," +
                "    genre VARCHAR(50) NOT NULL," +
                "    description TEXT," +
                "    rating DECIMAL(3,2)," +
                "    author_id BIGINT NOT NULL," +
                "    publisher_id BIGINT NOT NULL," +
                "    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors(id)," +
                "    CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publishers(id)" +
                ")"
            );
            
            // Create Book Tags table
            jdbcTemplate.execute(
                "CREATE TABLE book_tags (" +
                "    book_id BIGINT NOT NULL," +
                "    tag VARCHAR(100) NOT NULL," +
                "    CONSTRAINT fk_book_tags_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE," +
                "    PRIMARY KEY (book_id, tag)" +
                ")"
            );
            
            System.out.println("=== Created tables with correct schema ===");
            
            // Insert sample data
            insertSampleData(jdbcTemplate);
            
            System.out.println("=== Database initialization complete ===");
        };
    }
    
    private void insertSampleData(JdbcTemplate jdbcTemplate) {
        // Insert authors
        jdbcTemplate.execute(
            "INSERT INTO authors (first_name, last_name, email, birth_date, biography) VALUES " +
            "('John', 'Doe', 'john.doe@example.com', '1970-01-01', 'Bestselling fiction author')," +
            "('Jane', 'Smith', 'jane.smith@example.com', '1975-05-15', 'Science fiction specialist')," +
            "('Robert', 'Johnson', 'robert.johnson@example.com', '1980-12-25', 'Technology and programming expert')," +
            "('Emily', 'Brown', 'emily.brown@example.com', '1985-07-30', 'Mystery and thriller writer')"
        );
        
        // Insert publishers
        jdbcTemplate.execute(
            "INSERT INTO publishers (name, address, website) VALUES " +
            "('TechBooks Publishing', '123 Tech Street, Silicon Valley, CA', 'https://techbooks.com')," +
            "('Fiction House', '456 Story Lane, New York, NY', 'https://fictionhouse.com')," +
            "('Academic Press', '789 University Ave, Boston, MA', 'https://academicpress.com')," +
            "('Digital Media Corp', '321 Innovation Blvd, Austin, TX', 'https://digitalmedia.com')"
        );
        
        // Insert books
        jdbcTemplate.execute(
            "INSERT INTO books (title, isbn, price, published_date, genre, description, rating, author_id, publisher_id) VALUES " +
            "('Spring Boot Mastery', '978-1234567890', 49.99, '2023-01-15', 'TECHNOLOGY', 'Complete guide to Spring Boot development', 4.5, 3, 1)," +
            "('The Future Chronicles', '978-1234567891', 24.99, '2023-03-20', 'SCIENCE_FICTION', 'A thrilling journey through space and time', 4.2, 2, 2)," +
            "('Mystery at Midnight', '978-1234567892', 19.99, '2023-06-10', 'MYSTERY', 'A gripping mystery novel set in Victorian London', 4.0, 4, 2)," +
            "('GraphQL in Action', '978-1234567893', 54.99, '2023-09-05', 'TECHNOLOGY', 'Learn GraphQL from basics to advanced', 4.7, 3, 1)," +
            "('Digital Revolution', '978-1234567894', 29.99, '2023-11-12', 'NON_FICTION', 'How technology is changing our world', 4.3, 1, 4)"
        );
        
        // Insert book tags
        jdbcTemplate.execute(
            "INSERT INTO book_tags (book_id, tag) VALUES " +
            "(1, 'Java'), (1, 'Spring'), (1, 'Backend')," +
            "(2, 'Space'), (2, 'Future'), (2, 'Adventure')," +
            "(3, 'Victorian'), (3, 'Detective'), (3, 'Classic')," +
            "(4, 'GraphQL'), (4, 'API'), (4, 'Modern')," +
            "(5, 'Technology'), (5, 'Society'), (5, 'Digital')"
        );
        
        System.out.println("=== Inserted sample data ===");
    }
}
