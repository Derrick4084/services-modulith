package com.derocode.EcommApp.security.api;

public record UserLoginRequestDto(
        String email,
        String password
) {
}
