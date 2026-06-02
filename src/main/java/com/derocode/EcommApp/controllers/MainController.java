package com.derocode.EcommApp.controllers;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.AddProductRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductFacade productFacade;
    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;

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
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderDto request){
        try {
            OrderResponseDto response = orderFacade.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
