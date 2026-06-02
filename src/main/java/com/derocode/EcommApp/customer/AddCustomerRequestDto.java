package com.derocode.EcommApp.customer;

import com.derocode.EcommApp.customer.api.CustomerAddressRequestDto;

import java.util.List;

public record AddCustomerRequestDto(
        String firstName,
        String lastName,
        String email,
        List<CustomerAddressRequestDto>addresses
) {
}
