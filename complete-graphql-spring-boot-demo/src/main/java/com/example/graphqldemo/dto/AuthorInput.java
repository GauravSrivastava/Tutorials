package com.example.graphqldemo.dto;

import java.time.LocalDateTime;

public class AuthorInput {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthDate;
    private String biography;

    // Constructors
    public AuthorInput() {}

    public AuthorInput(String firstName, String lastName, String email, 
                       LocalDateTime birthDate, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.biography = biography;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDateTime birthDate) { this.birthDate = birthDate; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
}