package com.example.Pokemon_TCG_TEST.Model;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class User {

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format. Please provide a valid email.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Email must include a domain (e.g., .com, .net).")
    private String email;    // User email (used as the unique identifier)

    @NotBlank(message = "Password is required.")
    @Pattern(
            regexp = "^(?=.*[A-Z]).*$",
            message = "Password must contain at least one uppercase letter."
    )
    @Pattern(
            regexp = "^(?=.*[0-9]).*$",
            message = "Password must contain at least one number."
    )
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).*$",
            message = "Password must contain at least one special character."
    )
    @Pattern(
            regexp = "^.{8,}$",
            message = "Password must be at least 8 characters long."
    )
    private String password; // User password

    private List<String> favoritePokemons = new ArrayList<>();

    // Getters and Setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFavoritePokemons() {
        return favoritePokemons;
    }

    public void setFavoritePokemons(List<String> favoritePokemons) {
        this.favoritePokemons = favoritePokemons;
    }
}
