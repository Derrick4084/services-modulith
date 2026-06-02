package com.derocode.EcommApp.customer;

public record AddressResponseDto(
        String houseNumber,
        String street,
        String city,
        String state,
        String zipCode
) {
}
