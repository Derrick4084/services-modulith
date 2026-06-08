package com.derocode.EcommApp.security;

public record UserLoginRequestDto(
        String email,
        String password
) {
}
