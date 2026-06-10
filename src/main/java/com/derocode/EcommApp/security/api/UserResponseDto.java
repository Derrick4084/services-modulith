package com.derocode.EcommApp.security.api;

public record UserResponseDto(
        String firstName,
        String lastName,
        String userName,
        String role
) {
}
