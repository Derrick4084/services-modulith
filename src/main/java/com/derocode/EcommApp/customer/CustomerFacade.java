package com.derocode.EcommApp.customer;

public interface CustomerFacade {

    CustomerResponseDto getCustomerByEmail(String email);

    CustomerResponseDto addNewCustomer(AddCustomerRequestDto addCustomerRequestDto);
}
