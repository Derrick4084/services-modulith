package com.derocode.EcommApp.customer.api;


import jakarta.validation.constraints.Email;

public record CustomerByEmailDto(

        @Email(message = "Invalid email")
        String email
) {
}
