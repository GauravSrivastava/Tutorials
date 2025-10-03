# 🚀 GraphQL Spring Boot Mastery Project

<div align="center">

![GraphQL](https://img.shields.io/badge/GraphQL-E10098?style=for-the-badge&logo=graphql&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

**🎯 The Ultimate GraphQL Learning Experience**

*A comprehensive, production-ready Spring Boot application that showcases every aspect of modern GraphQL development - from basic queries to advanced schema evolution strategies.*

</div>

---

## 🌟 **Why This Project?**

This isn't just another GraphQL tutorial project. It's a **complete GraphQL ecosystem** that demonstrates real-world scenarios, best practices, and advanced concepts that you'll encounter in production environments. Whether you're a beginner looking to understand GraphQL fundamentals or an experienced developer wanting to see enterprise-grade implementation patterns, this project has you covered.

## 🎭 **What Makes This Project Special?**

### 🔥 **Complete Feature Coverage**
- **Every GraphQL concept** implemented with practical examples
- **Real-world scenarios** including error handling, validation, and performance optimization
- **Schema evolution** strategies showing how to handle API versioning gracefully
- **Advanced patterns** like union types, custom scalars, and complex filtering

### 🛠 **Production-Ready Architecture**
- **Clean code structure** following Spring Boot best practices
- **Comprehensive error handling** with structured error responses
- **Database integration** with JPA relationships and custom queries
- **Performance considerations** with field-level resolvers and N+1 query awareness

### 📚 **Educational Excellence**
- **Extensive documentation** with step-by-step explanations
- **Sample queries** covering every feature with detailed comments
- **Progressive complexity** from basic queries to advanced operations
- **Interactive learning** through GraphiQL interface

---

## 🎯 **What Does This Project Do?**

This project implements a **comprehensive book management system** using GraphQL as the API layer. But it's much more than a simple CRUD application:

### 📖 **Core Functionality**
- **Book Management**: Create, read, update, and delete books with rich metadata
- **Author Management**: Manage author information with biographical details
- **Publisher Management**: Handle publisher data and relationships
- **Advanced Search**: Multi-entity search across books, authors, and publishers
- **Complex Filtering**: Filter books by genre, price range, publication date, and more

### 🔬 **GraphQL Features Demonstrated**

| Feature Category | Implementation Details |
|------------------|----------------------|
| **🔧 Operations & Variables** | Dynamic queries with variable parameters, pagination, and complex filtering |
| **📊 Schema & Types** | Custom scalars (DateTime, BigDecimal), enums, unions, interfaces, and input types |
| **⚡ Queries & Mutations** | Full CRUD operations, batch operations, and complex nested queries |
| **🧩 Resolvers** | Field-level data fetchers, computed fields, and relationship resolution |
| **🔗 Fragments** | Reusable query components with composition and inline fragments |
| **🔄 Versioning** | Schema evolution with deprecation strategies and backward compatibility |

### 🎨 **Advanced Patterns**
- **Union Types**: Polymorphic search results returning different entity types
- **Custom Scalars**: Specialized data types for dates and precise decimals  
- **Error Handling**: Structured error responses with field-specific validation
- **Computed Fields**: Dynamic calculations like book age and display formatting
- **Relationship Loading**: Efficient data fetching for nested entities

---

## 📁 **Project Structure**

```
🏗️ graphql-demo/
├── 📄 pom.xml                                    # Maven build configuration
├── 📖 README.md                                  # This comprehensive guide
├── 📝 sample_queries.graphql                     # 50+ example GraphQL queries
│
└── 🎯 src/main/
    ├── ☕ java/com/example/graphqldemo/
    │   ├── 🚀 GraphqlDemoApplication.java        # Spring Boot main application
    │   │
    │   ├── ⚙️  config/
    │   │   └── GraphQLConfig.java                # Custom scalars & configuration
    │   │
    │   ├── 🗂️  entity/                           # JPA Entities
    │   │   ├── Book.java                         # Book entity with relationships
    │   │   ├── Author.java                       # Author entity with computed fields
    │   │   ├── Publisher.java                    # Publisher entity
    │   │   └── Genre.java                        # Genre enumeration
    │   │
    │   ├── 🗄️  repository/                       # Data Access Layer
    │   │   ├── BookRepository.java               # Custom queries & filtering
    │   │   ├── AuthorRepository.java             # Author data operations
    │   │   └── PublisherRepository.java          # Publisher data operations
    │   │
    │   ├── 🎮 controller/                        # GraphQL Endpoints
    │   │   ├── BookController.java               # Query & mutation handlers
    │   │   └── AuthorController.java             # Author operations
    │   │
    │   ├── 🔗 resolver/                          # Field Resolvers
    │   │   └── BookResolver.java                 # Nested field resolution
    │   │
    │   ├── 📦 dto/                               # Data Transfer Objects
    │   │   ├── BookInput.java                    # Book creation/update input
    │   │   ├── AuthorInput.java                  # Author input type
    │   │   └── BookFilter.java                   # Complex filtering input
    │   │
    │   └── 🔢 scalar/                            # Custom GraphQL Scalars
    │       └── DateTimeScalar.java               # LocalDateTime scalar implementation
    │
    └── 📋 resources/
        ├── ⚙️  application.properties            # Database & GraphQL configuration
        ├── 🗄️  schema.sql                        # Database schema definition
        ├── 📊 data.sql                           # Sample data for testing
        └── 📈 graphql/
            └── schema.graphqls                   # Complete GraphQL schema
```

---

## 🚀 **Getting Started - Build & Run**

### 📋 **Prerequisites**
- ☕ **Java 17+** (JDK 17 or higher)
- 🔧 **Maven 3.6+** (for dependency management)
- 🐘 **PostgreSQL 12+** (database server)
- 🌐 **Web browser** (for GraphiQL interface)

### 🗄️ **1. Database Setup**

```sql
-- Create the database
CREATE DATABASE graphql_demo;

-- Create a user (if needed)
CREATE USER postgres WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE graphql_demo TO postgres;

-- Verify connection
\c graphql_demo;
```

### ⚙️ **2. Configure Application**

Update database credentials in `src/main/resources/application.properties`:

```properties
# Update these with your PostgreSQL settings
spring.datasource.url=jdbc:postgresql://localhost:5432/graphql_demo
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 🛠️ **3. Build & Run**

```bash
# Clone/extract the project
cd graphql-demo

# Clean and build the project
mvn clean compile

# Run the application
mvn spring-boot:run

# Alternative: Build JAR and run
mvn clean package
java -jar target/graphql-demo-0.0.1-SNAPSHOT.jar
```

### ✅ **4. Verify Setup**

- **Application**: http://localhost:8080
- **GraphiQL Interface**: http://localhost:8080/graphiql
- **GraphQL Endpoint**: http://localhost:8080/graphql

---

## 🎮 **Explore & Learn - Your GraphQL Journey**

Once your application is running, here's your comprehensive learning path:

### 🌟 **Phase 1: GraphQL Fundamentals**

#### 🔍 **1. Explore the GraphiQL Interface**
- Navigate to `http://localhost:8080/graphiql`
- **Discover**: Interactive query editor with auto-completion
- **Try**: Schema introspection using the "Docs" panel
- **Learn**: Real-time query validation and error highlighting

#### 📊 **2. Schema Exploration**
```graphql
# Introspect the entire schema
query GetSchema {
  __schema {
    types {
      name
      kind
      description
    }
  }
}

# Explore a specific type
query ExploreBookType {
  __type(name: "Book") {
    name
    fields {
      name
      type { name }
      description
    }
  }
}
```

#### 🎯 **3. Basic Query Operations**
```graphql
# Simple data fetching
query GetBooks {
  books {
    id
    title
    price
    author {
      fullName
    }
  }
}

# Query with variables
query GetBooksFiltered($limit: Int, $genre: Genre) {
  books(limit: $limit, filter: { genre: $genre }) {
    title
    displayPrice
    ageInYears
  }
}
```

### 🚀 **Phase 2: Advanced GraphQL Patterns**

#### 🧩 **4. Master Fragments**
```graphql
# Define reusable fragments
fragment BookDetails on Book {
  id
  title
  isbn
  price
  displayPrice
  genre
  rating
  tags
}

# Use fragments in queries
query BooksWithFragments {
  books(limit: 5) {
    ...BookDetails
    author {
      fullName
      email
    }
  }
}
```

#### 🔄 **5. Mutation Operations**
```graphql
# Create a new book
mutation CreateBook($input: BookInput!) {
  createBook(input: $input) {
    success
    book {
      ...BookDetails
    }
    errors {
      field
      message
      code
    }
  }
}

# Variables:
{
  "input": {
    "title": "GraphQL Mastery",
    "isbn": "978-1234567890",
    "price": 59.99,
    "publishedDate": "2024-01-15T10:00:00",
    "authorId": "1",
    "publisherId": "1",
    "genre": "TECHNOLOGY",
    "description": "Master GraphQL development"
  }
}
```

#### 🔍 **6. Complex Filtering & Search**
```graphql
# Advanced filtering
query FilterBooks($filter: BookFilter!) {
  books(filter: $filter) {
    title
    price
    publishedDate
    author { fullName }
    publisher { name }
  }
}

# Multi-entity search (Union types)
query SearchEverything($query: String!) {
  searchBooks(query: $query) {
    ... on Book { title author { fullName } }
    ... on Author { fullName email }
    ... on Publisher { name address }
  }
}
```

### 🎓 **Phase 3: Production Patterns & Best Practices**

#### ⚡ **7. Performance Optimization**
- **Field Selection**: Only request needed fields
- **Resolver Efficiency**: Observe N+1 query prevention
- **Pagination**: Use limit/offset for large datasets
- **Caching**: Understand resolver-level caching strategies

#### 🔄 **8. Schema Evolution & Versioning**
```graphql
# Using deprecated fields (backward compatibility)
query DeprecatedFieldUsage {
  authors {
    id
    name      # DEPRECATED: use firstName/lastName
    fullName  # NEW: recommended approach
  }
}

# New fields in existing types
query NewFieldsExample {
  books {
    title
    tags      # Added in v2.0
    rating    # Added in v2.0
  }
}
```

#### 🛡️ **9. Error Handling Patterns**
```graphql
# Structured error responses
mutation CreateBookWithValidation($input: BookInput!) {
  createBook(input: $input) {
    success
    book { id title }
    errors {
      field     # Which field has the error
      message   # Human-readable error
      code      # Machine-readable error code
    }
  }
}
```

### 🏆 **Phase 4: Advanced Exploration**

#### 🧪 **10. Custom Scalars in Action**
- **DateTime**: Observe ISO format handling
- **BigDecimal**: Test precise decimal operations
- **Validation**: See how custom scalars validate input

#### 🔗 **11. Relationship Resolution**
- **Lazy Loading**: Understand JPA fetch strategies
- **DataFetcher Pattern**: See field-level resolution
- **Computed Fields**: Explore dynamic calculations

#### 📊 **12. Batch Operations**
```graphql
# Create multiple books at once
mutation CreateMultipleBooks($books: [BookInput!]!) {
  createBooks(input: $books) {
    success
    book { title }
    errors { field message }
  }
}
```

### 🔬 **Phase 5: Deep Dive Analysis**

#### 📈 **13. Monitor & Debug**
- **SQL Logging**: Check console for generated queries
- **Performance**: Time your operations
- **Memory Usage**: Observe resolver efficiency
- **Error Patterns**: Test validation scenarios

#### 🎯 **14. Extend the Project**
- **Add New Types**: Create categories, reviews, etc.
- **Custom Operations**: Implement analytics queries
- **Authentication**: Add user management
- **Subscriptions**: Implement real-time updates

---

## 🎊 **Learning Outcomes**

By exploring this project, you will master:

✅ **GraphQL Core Concepts**: Queries, mutations, subscriptions, and schema design
✅ **Spring Integration**: Annotations, configuration, and best practices  
✅ **Advanced Patterns**: Unions, interfaces, custom scalars, and fragments
✅ **Performance Optimization**: Resolver patterns and N+1 query prevention
✅ **API Evolution**: Versioning strategies and backward compatibility
✅ **Production Readiness**: Error handling, validation, and monitoring
✅ **Database Integration**: JPA relationships and complex queries
✅ **Real-World Architecture**: Clean code structure and separation of concerns

## 🌐 **API Endpoints**

| Endpoint | Purpose | Access |
|----------|---------|--------|
| `http://localhost:8080/graphql` | GraphQL API endpoint | POST requests |
| `http://localhost:8080/graphiql` | Interactive GraphQL IDE | Web browser |
| `http://localhost:8080/actuator/health` | Health check | GET request |

## 🤝 **Contributing & Extending**

This project is designed to be educational and extensible. Feel free to:
- 🔧 Add new features and entity types
- 🧪 Experiment with different GraphQL patterns
- 📚 Enhance documentation and examples
- 🚀 Share your learning experiences

---

<div align="center">

**🎯 Ready to become a GraphQL master?**

*Start your engines and dive into the most comprehensive GraphQL learning experience available!*

**Happy Coding! 🚀**

</div>