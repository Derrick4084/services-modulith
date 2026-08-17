package com.derocode.EcommApp.security.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Email(message = "Invalid email")
        String email,
        @Size(min = 8, message = "Password length must be at least 8 characters")
        String password
) {
}
