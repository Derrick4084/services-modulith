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
import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import com.derocode.EcommApp.security.api.UserResponseDto;
import com.derocode.EcommApp.security.services.AppUserService;
import com.derocode.EcommApp.security.services.AuthenticationService;
import com.derocode.EcommApp.security.services.JwtService;
import com.derocode.EcommApp.security.models.User;
import com.derocode.EcommApp.security.mappers.UserMapperImpl;
import com.derocode.EcommApp.security.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final UserMapperImpl mapper;
    private final AppUserService appUserService;
    private final AuthenticationService authenticationService;


    @GetMapping("/api/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){

        ProductResponseDto response = productFacade.getProductById(id);
        return ResponseEntity.ok(response);

//        try {
//            ProductResponseDto response = productFacade.getProductById(id);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
    }

    @PostMapping("/api/product/add")
    public ResponseEntity<Object> addProduct(@RequestBody AddProductRequestDto request){
        ProductResponseDto response = productFacade.addNewProduct(request);
        return ResponseEntity.ok(response);
//        try {
//            ProductResponseDto response = productFacade.addNewProduct(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
    }


    @PostMapping("/api/customer/add")
    public ResponseEntity<Object> addCustomer(@RequestBody AddCustomerRequestDto request){
        CustomerResponseDto response = customerFacade.addNewCustomer(request);
        return ResponseEntity.ok(response);

    }




    @GetMapping("/api/customer/email/{email}")
    public ResponseEntity<Object> getCustomerByEmail(@PathVariable String email){

        CustomerResponseDto response = customerFacade.getCustomerByEmail(email);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/api/order/create")
    public ResponseEntity<Object> createOrder(@RequestBody @NonNull CreateOrderDto request){

        if(!customerFacade.existsByEmail(request.customerEmail())) {
            return ResponseEntity.badRequest().body("No customer found to create this order");
        }
        OrderResponseDto response = orderFacade.createOrder(request);
        return ResponseEntity.ok(response);

    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> login(@RequestBody @NonNull UserLoginRequestDto loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @PostMapping("/adm/user/add")
    public ResponseEntity<Object> addUser(@RequestBody CreateUserDto request){
        try {
            UserResponseDto response = appUserService.addNewUser(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/adm/user/{email}")
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





