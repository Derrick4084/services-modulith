package com.derocode.EcommApp.security.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email(message = "Invalid email")
        String email,
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password
) {
}
