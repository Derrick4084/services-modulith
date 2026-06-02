package com.derocode.EcommApp.customer;

import java.util.List;

public record CustomerResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<AddressResponseDto> addresses
) {
}
