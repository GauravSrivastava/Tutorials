-- Drop existing tables to recreate with correct schema
DROP TABLE IF EXISTS book_tags CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS publishers CASCADE;
DROP TABLE IF EXISTS authors CASCADE;

-- Create Authors table
CREATE TABLE IF NOT EXISTS authors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    birth_date TIMESTAMP,
    biography TEXT
);

-- Create Publishers table
CREATE TABLE IF NOT EXISTS publishers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL,
    website VARCHAR(255)
);

-- Create Books table
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    published_date TIMESTAMP NOT NULL,
    genre VARCHAR(50) NOT NULL,
    description TEXT,
    rating DECIMAL(3,2),
    author_id BIGINT NOT NULL,
    publisher_id BIGINT NOT NULL,
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES authors(id),
    CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publishers(id)
);

-- Create Book Tags table (for many-to-many relationship)
CREATE TABLE IF NOT EXISTS book_tags (
    book_id BIGINT NOT NULL,
    tag VARCHAR(100) NOT NULL,
    CONSTRAINT fk_book_tags_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, tag)
);