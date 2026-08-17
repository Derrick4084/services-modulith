package com.derocode.EcommApp.customer.services;



import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.customer.mapper.CustomerMapperImpl;
import com.derocode.EcommApp.customer.models.Customer;
import com.derocode.EcommApp.customer.repositories.CustomerMongoRepository;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerService customerService;
    private final CustomerMapperImpl customerMapper;
    private final CustomerMongoRepository customerMongoRepository;

    @Override
    public CustomerResponseDto getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);

    }

    @Override
    public CustomerResponseDto addNewCustomer(@NonNull AddCustomerRequestDto addCustomerRequestDto) {


        Customer customer = customerService.addNewCustomer(addCustomerRequestDto);
        return customerMapper.entityToResponse(customer);
    }

    @Override
    public Customer loadCustomerByEmail(String email) {
        return customerMongoRepository.getCustomerByEmail(email).orElseThrow(()-> new SharedResourceNotFoundException(
                "Customer does not exists"
        ));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerService.existsByEmail(email);
    }
}
