package com.derocode.EcommApp;

import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.customer.api.CustomerAddressRequestDto;
import com.derocode.EcommApp.customer.repositories.CustomerMongoRepository;
import com.derocode.EcommApp.customer.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMongoRepository repository;


    @AfterEach
    void cleanup() {
        repository.deleteByEmail("bthomas@test.com");
    }

    @Test
    void shouldCreateCustomer() {

        List<CustomerAddressRequestDto> addresses = new ArrayList<>();

        CustomerAddressRequestDto address1 = new CustomerAddressRequestDto(
                "1313",
                "Mockingbird lane",
                "Somewhere",
                "CA",
                "35592-444"
        );
        CustomerAddressRequestDto address2 = new CustomerAddressRequestDto(
                "1298",
                "Jackson St",
                "Nowhere",
                "NY",
                "98354-333"
        );

        addresses.add(address1);
        addresses.add(address2);

        AddCustomerRequestDto dto = new AddCustomerRequestDto(
                "Bob",
                "Thomas",
                "bthomas@test.com",
                addresses
        );

        CustomerResponseDto response =
                customerService.addNewCustomer(dto);

        assertThat(response.email())
                .isEqualTo("bthomas@test.com");

        assertThat(response.addresses().getFirst().street())
                .isEqualTo("Mockingbird lane");

        assertThat(repository.existsByEmail("bthomas@test.com"));

    }
}
