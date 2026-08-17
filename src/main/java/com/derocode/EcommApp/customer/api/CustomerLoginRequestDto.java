package com.derocode.EcommApp.customer.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CustomerLoginRequestDto(
        @Email(message = "Invalid email")
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters")
        String password
) {
}
