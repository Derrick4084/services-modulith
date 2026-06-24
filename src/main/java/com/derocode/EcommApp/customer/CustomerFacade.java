package com.derocode.EcommApp.customer;

import com.derocode.EcommApp.customer.models.Customer;

public interface CustomerFacade {

    CustomerResponseDto getCustomerByEmail(String email);

    CustomerResponseDto addNewCustomer(AddCustomerRequestDto addCustomerRequestDto);

    Customer loadCustomerByEmail(String email);

    Boolean existsByEmail(String email);
}
