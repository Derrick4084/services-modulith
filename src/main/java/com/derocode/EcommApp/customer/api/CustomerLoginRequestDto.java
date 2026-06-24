package com.derocode.EcommApp.customer.api;

public record CustomerLoginRequestDto(
        String email,
        String password
) {
}
