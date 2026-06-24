package com.derocode.EcommApp;


import com.derocode.EcommApp.customer.services.CustomerService;
import com.derocode.EcommApp.exceptions.SharedResourceExistsException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.services.AppUserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class CustomExceptionsTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    AppUserDetailService appUserService;

    @Test
    void shouldThrowResourceNotFoundException(){

        assertThatThrownBy(()-> customerService.getCustomerByEmail("sdoe@example.com"))
                .isInstanceOf(SharedResourceNotFoundException.class)
                .hasMessageContaining("Customer not found");

    }


    @Test
    void shouldThrowResourceExistsException(){
        assertThatThrownBy(()-> appUserService.createUser(new CreateUserDto(
                "Admin",
                "Admin",
                "admin@example.com",
                "abc123"

        ))).isInstanceOf(SharedResourceExistsException.class)
                .hasMessageContaining("User with this email admin@example.com already exists");

    }

}
