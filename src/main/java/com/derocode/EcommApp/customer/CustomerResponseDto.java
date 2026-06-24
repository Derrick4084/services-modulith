package com.derocode.EcommApp.customer;

import java.util.List;

public record CustomerResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        List<AddressResponseDto> addresses
) {
}
