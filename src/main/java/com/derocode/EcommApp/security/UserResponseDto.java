package com.derocode.EcommApp.security;

public record UserResponseDto(
        String firstName,
        String lastName,
        String userName,
        String role
) {
}
