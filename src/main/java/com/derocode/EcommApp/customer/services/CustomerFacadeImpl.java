package com.derocode.EcommApp.customer.services;



import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerService customerService;

    @Override
    public CustomerResponseDto getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);

    }

    @Override
    public CustomerResponseDto addNewCustomer(@NonNull AddCustomerRequestDto addCustomerRequestDto) {
        return customerService.addNewCustomer(addCustomerRequestDto);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerService.existsByEmail(email);
    }
}
