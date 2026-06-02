package com.derocode.EcommApp.customer.api;

public record CustomerAddressRequestDto(
        String houseNumber,
        String street,
        String city,
        String state,
        String zipCode
) {
}
