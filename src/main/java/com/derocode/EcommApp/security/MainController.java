package com.derocode.EcommApp.security;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.AddProductRequestDto;
import com.derocode.EcommApp.security.internals.JwtService;
import com.derocode.EcommApp.security.internals.User;
import com.derocode.EcommApp.security.internals.UserMapperImpl;
import com.derocode.EcommApp.security.internals.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductFacade productFacade;
    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapperImpl mapper;


    @GetMapping("/api/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        try {
            ProductResponseDto response = productFacade.getProductById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/api/product/add")
    public ResponseEntity<Object> addProduct(@RequestBody AddProductRequestDto request){
        try {
            ProductResponseDto response = productFacade.addNewProduct(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/api/customer/add")
    public ResponseEntity<Object> addCustomer(@RequestBody AddCustomerRequestDto request){
        try {
            CustomerResponseDto response = customerFacade.addNewCustomer(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/customer/email/{email}")
    public ResponseEntity<Object> getCustomerByEmail(@PathVariable String email){
        try {
            CustomerResponseDto response = customerFacade.getCustomerByEmail(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/api/order/create")
    public ResponseEntity<Object> createOrder(@RequestBody @NonNull CreateOrderDto request){

        if(!customerFacade.existsByEmail(request.customerEmail())) {
            return ResponseEntity.badRequest().body("No customer found to create this order");
        }

        try {
            OrderResponseDto response = orderFacade.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@RequestBody @NonNull UserLoginRequestDto loginRequest) {
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.email(), loginRequest.password()
            );
            Authentication authenticationResponse = this.authManager.authenticate(authenticationRequest);
            String token = jwtService.generateTokenWithRoles(authenticationResponse);
            return ResponseEntity.ok(token);
        }
        catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/api/user/{email}")
    public ResponseEntity<Object> findUser(@PathVariable @NonNull String email) {

        if(userRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User response = userRepository.findByEmail(email).get();
        return ResponseEntity.ok(mapper.userToResponseDto(response));

    }

    @PostMapping("/data")
    public ResponseEntity<String> getData() {
            return ResponseEntity.ok("This is the secure data");
        }


}





