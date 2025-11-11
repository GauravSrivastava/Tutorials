# 🔐 Dynamic Role-Based Field-Level Security in Spring Boot APIs with Jackson @JsonView

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Jackson](https://img.shields.io/badge/Jackson-2.x-blue?style=flat-square&logo=java)](https://github.com/FasterXML/jackson)
[![Java](https://img.shields.io/badge/Java-11%2B-orange?style=flat-square&logo=java)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Production%20Ready-success?style=flat-square)](https://github.com)

*A powerful guide to implementing granular, role-based field-level security in your Spring Boot REST APIs using Jackson's @JsonView annotation combined with Spring Security*

[Quick Start](#-quick-start) • [Features](#-features) • [Examples](#-code-examples) • [Best Practices](#-best-practices)

</div>

---

## 🎯 Overview

Protecting sensitive data in your REST APIs is crucial. This guide demonstrates how to leverage **Jackson's @JsonView** combined with **Spring Security** to control which fields are serialized based on user roles—without duplicating DTOs or creating complex conditional logic.

### ✨ Key Benefits

- 🎭 **Role-Based Access Control** - Different views for different user roles
- 🔄 **Zero Duplication** - Single entity, multiple serialization outputs
- 🚀 **Performance Optimized** - Minimal overhead, leverages Jackson's native capabilities
- 🛡️ **Security First** - Field-level granularity, not endpoint-level
- 💡 **Clean Architecture** - Separation of concerns between data and presentation

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    REST API Request                         │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
            ┌────────────────────────┐
            │  Spring Security       │
            │  Extract User Roles    │
            └────────┬───────────────┘
                     │
        ┌────────────┴─────────────┐
        │                          │
        ▼                          ▼
    ┌─────────┐            ┌──────────────┐
    │ Admin   │            │ User/Public  │
    │ View    │            │ View         │
    └────┬────┘            └──────┬───────┘
         │                        │
         └────────┬───────────────┘
                  │
                  ▼
      ┌──────────────────────────┐
      │  MappingJacksonValue     │
      │  + Serialization View    │
      └────────┬─────────────────┘
               │
               ▼
      ┌──────────────────────────┐
      │  JSON Response           │
      │  (Filtered Fields Only)  │
      └──────────────────────────┘
```

---

## 🚀 Quick Start

### 1️⃣ Define View Interfaces

```java
// Views represent access levels
public class Views {
    public static class Public { }
    public static class User extends Public { }
    public static class Admin extends User { }
}
```

### 2️⃣ Annotate Your Entity

```java
@Entity
public class UserEntity {
    @JsonView(Views.Public.class)
    private String username;

    @JsonView(Views.User.class)
    private String email;

    @JsonView(Views.Admin.class)
    private String secretAdminInfo;

    // getters and setters...
}
```

### 3️⃣ Configure Your Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public MappingJacksonValue getUser(@PathVariable Long id, Authentication auth) {
        UserEntity user = userService.findById(id);
        
        // Determine view based on role
        Class<?> jsonView = Views.Public.class;
        if (auth != null && auth.getAuthorities() != null) {
            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isUser = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
            
            if (isAdmin) {
                jsonView = Views.Admin.class;
            } else if (isUser) {
                jsonView = Views.User.class;
            }
        }
        
        MappingJacksonValue wrapper = new MappingJacksonValue(user);
        wrapper.setSerializationView(jsonView);
        return wrapper;
    }
}
```

---

## 📋 Jackson @JsonView for Role-Based Field Access

The **@JsonView** annotation provides a clean mechanism to control field serialization:

### How It Works

1. **Define Multiple Interface Views** - Create views representing different access levels (e.g., `PublicView`, `UserView`, `AdminView`)
2. **Annotate Entity Fields** - Mark each field with `@JsonView` to specify which views can see it
3. **Select View at Runtime** - In your controller, determine the appropriate view based on the authenticated user's role
4. **Switch Serialization Output** - The same controller method returns different JSON based on the selected view

### Hierarchy

Views support inheritance for cleaner organization:

```java
// More restrictive
public static class Public { }

// Extends Public - inherits its fields
public static class User extends Public { }

// Extends User - inherits from both User and Public
public static class Admin extends User { }
```

---

## 💻 Complete Code Examples

### Example 1: Basic User Entity with Role-Based Views

```java
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Long id;
    
    @JsonView(Views.Public.class)
    private String username;
    
    @JsonView(Views.User.class)
    private String email;
    
    @JsonView(Views.User.class)
    private String phoneNumber;
    
    @JsonView(Views.Admin.class)
    private String internalNotes;
    
    @JsonView(Views.Admin.class)
    private LocalDateTime lastLoginDate;
    
    @JsonView(Views.Admin.class)
    private String sensitiveSecurityData;
    
    // Constructors, getters, setters...
}
```

### Example 2: Enhanced Controller with Role Detection

```java
@RestController
@RequestMapping("/api/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public MappingJacksonValue getUserProfile(
            @PathVariable Long id, 
            Authentication authentication) {
        
        UserEntity user = userService.findById(id);
        
        // Determine appropriate view
        Class<?> serializationView = determineViewByRole(authentication);
        
        // Wrap response with selected view
        MappingJacksonValue wrapper = new MappingJacksonValue(user);
        wrapper.setSerializationView(serializationView);
        
        return wrapper;
    }
    
    private Class<?> determineViewByRole(Authentication auth) {
        if (auth == null) {
            return Views.Public.class;
        }
        
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        boolean isUser = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
        
        if (isAdmin) {
            return Views.Admin.class;
        } else if (isUser) {
            return Views.User.class;
        }
        
        return Views.Public.class;
    }
}
```

### Example 3: Handling Collections

```java
@GetMapping("/all")
public MappingJacksonValue getAllUsers(Authentication auth) {
    List<UserEntity> users = userService.findAll();
    
    MappingJacksonValue wrapper = new MappingJacksonValue(users);
    wrapper.setSerializationView(determineViewByRole(auth));
    
    return wrapper;
}
```

---

## 🔧 Advanced Techniques

### Conditional Field Serialization with Custom Serializers

```java
@Component
public class RoleAwareSerializer extends StdSerializer<UserEntity> {
    
    public RoleAwareSerializer() {
        super(UserEntity.class);
    }
    
    @Override
    public void serialize(UserEntity user, JsonGenerator gen, 
                         SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("username", user.getUsername());
        
        // Custom logic based on context
        if (isUserAuthorized()) {
            gen.writeStringField("email", user.getEmail());
        }
        
        gen.writeEndObject();
    }
}
```

### Combining @JsonView with @JsonIgnore

```java
@JsonView(Views.User.class)
@JsonIgnore  // Can still ignore at runtime if needed
private String temporaryField;
```

### Alternative: DTOs (When @JsonView Isn't Enough)

```java
// Use when you need more complex transformations
@Data
public class UserPublicDTO {
    private Long id;
    private String username;
}

@Data
public class UserAdminDTO {
    private Long id;
    private String username;
    private String email;
    private String secretAdminInfo;
    private LocalDateTime lastLoginDate;
}
```

---

## 🎓 Best Practices

| Practice | Benefit | Example |
|----------|---------|---------|
| **Use View Hierarchy** | Reduces duplication, maintains DRY principle | `User extends Public` |
| **Naming Convention** | Clear, consistent, easy to understand | `Views.Admin`, `Views.User`, `Views.Public` |
| **Extract Role Logic** | Reusable, testable, maintainable | Create `RoleViewResolver` utility class |
| **Document Views** | Future-proof your API | Add JavaDoc explaining field visibility |
| **Test All Views** | Ensure security isn't bypassed | Unit test each view independently |
| **Combine with Method Security** | Defense in depth approach | Use `@PreAuthorize` on endpoints |

---

## 📊 Comparison: @JsonView vs Alternatives

| Approach | Complexity | Performance | Duplication | Recommended |
|----------|-----------|------------|------------|-------------|
| **@JsonView** | Low | High | None | ✅ YES - Best |
| **Custom Serializers** | Medium | High | None | For complex cases |
| **DTOs** | High | High | High | Only if needed |
| **@JsonIgnore Logic** | Medium | Medium | None | Not recommended |
| **Multiple Endpoints** | Very High | High | Very High | Avoid |

---

## 🛡️ Security Considerations

- ✅ **Always validate roles** on the backend, never trust client claims
- ✅ **Default to restrictive** - Prefer denying access unless explicitly granted
- ✅ **Combine with endpoint security** - Use `@PreAuthorize` in addition to field-level control
- ✅ **Test serialization** - Verify sensitive fields aren't accidentally exposed
- ✅ **Log access patterns** - Monitor who's accessing what data
- ✅ **Use HTTPS** - Always encrypt data in transit

---

## ⚙️ Configuration Tips

### application.yml

```yaml
spring:
  jackson:
    serialization:
      write-dates-as-timestamps: false
      indent-output: true
    default-property-inclusion: non_null

security:
  enable-field-level-access-control: true
```

### Maven Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

---

## 🧪 Testing Example

```java
@SpringBootTest
public class UserControllerSecurityTest {
    
    @Test
    public void testPublicViewDoesNotIncludeEmail() {
        // Assert that email field is not serialized for PUBLIC view
    }
    
    @Test
    public void testAdminViewIncludesAllFields() {
        // Assert that all sensitive fields are visible to admins
    }
    
    @Test
    public void testUserViewIncludesOwnEmail() {
        // Assert that users can see their own email
    }
}
```

---

## 🎯 Common Use Cases

### 🏦 Banking API
- **Public**: Account holder name, account type
- **User**: Balance, transactions
- **Admin**: All + internal risk score, compliance notes

### 🏥 Healthcare Platform
- **Public**: Doctor specialization
- **User**: Appointment history, prescriptions
- **Admin**: All + patient SSN, medical records, billing info

### 📱 Social Media
- **Public**: Username, profile picture
- **User**: Followers, follows, connections
- **Admin**: All + IP logs, account metadata, enforcement flags

---

## 📚 Resources & References

- [Jackson Annotations Documentation](https://github.com/FasterXML/jackson-annotations)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- [Spring Boot Best Practices](https://spring.io/guides)

---

## 💡 Pro Tips

1. **Create a Utility Class** for role resolution to avoid controller clutter:
   ```java
   public class RoleViewResolver {
       public static Class<?> resolveView(Authentication auth) {
           // centralized logic
       }
   }
   ```

2. **Cache Role Information** in a custom authentication object for performance

3. **Use AOP** for cross-cutting field security concerns

4. **Monitor Serialization** in production to catch unintended data exposure

5. **Version Your APIs** separately for different client types

---

## 📝 Summary

| Step | Action | Key Point |
|------|--------|-----------|
| 1 | Create View interfaces | Represent different access levels |
| 2 | Annotate entity fields | Mark which roles can see which fields |
| 3 | Get user's role | Use `SecurityContextHolder` or method parameter |
| 4 | Select appropriate view | Map role to view class |
| 5 | Wrap entity in `MappingJacksonValue` | Set serialization view before returning |
| 6 | Return wrapped object | Jackson handles field filtering automatically |

---

<div align="center">

**This is a clean, scalable, and production-ready approach to field-level security in Spring Boot APIs.**

⭐ Found this helpful? Star this repo! ⭐

</div>

---

*Last Updated: November 2025*
