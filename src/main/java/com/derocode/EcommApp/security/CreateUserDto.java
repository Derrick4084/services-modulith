package com.derocode.EcommApp.security;

public record CreateUserDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String role
) {
}
