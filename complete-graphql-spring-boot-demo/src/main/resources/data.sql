-- Insert sample authors
INSERT INTO authors (first_name, last_name, email, birth_date, biography) VALUES
('John', 'Doe', 'john.doe@example.com', '1970-01-01', 'Bestselling fiction author'),
('Jane', 'Smith', 'jane.smith@example.com', '1975-05-15', 'Science fiction specialist'),
('Robert', 'Johnson', 'robert.johnson@example.com', '1980-12-25', 'Technology and programming expert'),
('Emily', 'Brown', 'emily.brown@example.com', '1985-07-30', 'Mystery and thriller writer');

-- Insert sample publishers
INSERT INTO publishers (name, address, website) VALUES
('TechBooks Publishing', '123 Tech Street, Silicon Valley, CA', 'https://techbooks.com'),
('Fiction House', '456 Story Lane, New York, NY', 'https://fictionhouse.com'),
('Academic Press', '789 University Ave, Boston, MA', 'https://academicpress.com'),
('Digital Media Corp', '321 Innovation Blvd, Austin, TX', 'https://digitalmedia.com');

-- Insert sample books
INSERT INTO books (title, isbn, price, published_date, genre, description, rating, author_id, publisher_id) VALUES
('Spring Boot Mastery', '978-1234567890', 49.99, '2023-01-15', 'TECHNOLOGY', 'Complete guide to Spring Boot development', 4.5, 3, 1),
('The Future Chronicles', '978-1234567891', 24.99, '2023-03-20', 'SCIENCE_FICTION', 'A thrilling journey through space and time', 4.2, 2, 2),
('Mystery at Midnight', '978-1234567892', 19.99, '2023-06-10', 'MYSTERY', 'A gripping mystery novel set in Victorian London', 4.0, 4, 2),
('GraphQL in Action', '978-1234567893', 54.99, '2023-09-05', 'TECHNOLOGY', 'Learn GraphQL from basics to advanced', 4.7, 3, 1),
('Digital Revolution', '978-1234567894', 29.99, '2023-11-12', 'NON_FICTION', 'How technology is changing our world', 4.3, 1, 4);

-- Insert sample book tags
INSERT INTO book_tags (book_id, tag) VALUES
(1, 'Java'), (1, 'Spring'), (1, 'Backend'),
(2, 'Space'), (2, 'Future'), (2, 'Adventure'),
(3, 'Victorian'), (3, 'Detective'), (3, 'Classic'),
(4, 'GraphQL'), (4, 'API'), (4, 'Modern'),
(5, 'Technology'), (5, 'Society'), (5, 'Digital');