package com.derocode.EcommApp.customer;

import com.derocode.EcommApp.customer.api.CustomerAddressRequestDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AddCustomerRequestDto(

        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String email,
        @NotBlank
        String password,
        List<CustomerAddressRequestDto>addresses
) {
}
