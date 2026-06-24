package com.derocode.EcommApp.customer;


import com.derocode.EcommApp.customer.api.CustomerLoginRequestDto;
import com.derocode.EcommApp.customer.mapper.CustomerMapperImpl;
import com.derocode.EcommApp.customer.models.Customer;
import com.derocode.EcommApp.customer.services.CustomerAuthenticationService;
import com.derocode.EcommApp.customer.services.CustomerService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapperImpl customerMapper;

    private final CustomerAuthenticationService authenticationService;

    public CustomerController(CustomerService customerService, CustomerMapperImpl customerMapper, CustomerAuthenticationService authenticationService) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/customer/authenticate")
    public ResponseEntity<String> authenticateCustomer(@RequestBody @NonNull CustomerLoginRequestDto loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticateCustomer(loginRequest));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/customer/add")
    public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody AddCustomerRequestDto request){
        Customer customer = customerService.addNewCustomer(request);
        return ResponseEntity.ok(customerMapper.entityToResponse(customer));
    }

    @GetMapping("/customer/email/{email}")
    public ResponseEntity<CustomerResponseDto> getCustomerByEmail(@PathVariable String email){
        Customer response = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customerMapper.entityToResponse(response));
    }


    @GetMapping("/customer/all")
    public ResponseEntity<Page<CustomerResponseDto>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<CustomerResponseDto> response = customerService.getAll(0,10);
        return ResponseEntity.ok(response);
    }
}
