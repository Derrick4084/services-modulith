package com.derocode.EcommApp.cart;

import com.derocode.EcommApp.cart.api.CartItemDto;
import com.derocode.EcommApp.cart.api.CartResponseDto;
import com.derocode.EcommApp.cart.api.ProcessCartDto;
import com.derocode.EcommApp.cart.mapper.CartMapperImpl;
import com.derocode.EcommApp.cart.models.Cart;
import com.derocode.EcommApp.cart.service.CartService;
import com.derocode.EcommApp.order.OrderResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final CartMapperImpl cartMapper;

    @GetMapping("/cart/find")
    public ResponseEntity<CartResponseDto> findCart(@NonNull Authentication authentication){
        Cart cart = cartService.getCart(authentication.getName());
        return ResponseEntity.ok().body(cartMapper.cartToDto(cart));
    }

    @PostMapping("/cart/addItem")
    public ResponseEntity<CartResponseDto> addItem(@Valid @RequestBody @NonNull CartItemDto dto, @NonNull Authentication authentication){
        Cart cart = cartService.addCartItem(authentication.getName(), dto);
        return ResponseEntity.ok().body(cartMapper.cartToDto(cart));
    }

    @PostMapping("/cart/removeItem")
    public ResponseEntity<CartResponseDto> removeItem(@Valid @RequestBody @NonNull CartItemDto dto, @NonNull Authentication authentication){
        Cart cart = cartService.removeCartItem(authentication.getName(), dto);
        return ResponseEntity.ok().body(cartMapper.cartToDto(cart));
    }

    @PostMapping("/cart/process")
    public ResponseEntity<OrderResponseDto> processCart(@Valid @RequestBody @NonNull ProcessCartDto dto, @NonNull Authentication authentication){
        String paymentMethod = dto.paymentMethod();
        OrderResponseDto response = cartService.processCart(paymentMethod, authentication.getName());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart/{email}")
    public ResponseEntity<CartResponseDto> byEmail(@PathVariable String email){
        Cart cart = cartService.getCart(email);
        return ResponseEntity.ok().body(cartMapper.cartToDto(cart));
    }

}
