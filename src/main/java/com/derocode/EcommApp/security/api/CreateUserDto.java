package com.derocode.EcommApp.security.api;

public record CreateUserDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {
}
