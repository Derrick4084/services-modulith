package com.derocode.EcommApp.cart.service;


import com.derocode.EcommApp.cart.api.CartItemDto;
import com.derocode.EcommApp.cart.api.CartResponseDto;
import com.derocode.EcommApp.cart.mapper.CartMapperImpl;
import com.derocode.EcommApp.cart.models.Cart;
import com.derocode.EcommApp.cart.models.CartItem;
import com.derocode.EcommApp.cart.repository.CartRepository;
import com.derocode.EcommApp.exceptions.SharedFailedAuthException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.CreateOrderItemDto;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;


@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductFacade productFacade;
//    private final CartMapperImpl mapper;
    private final OrderFacade orderFacade;

    private void updateCartTotal(@NonNull Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }


    public Cart getCart(@NonNull String email){

        return cartRepository.findByCustomerEmail(email).orElseThrow(()->
                new SharedResourceNotFoundException("Cart not found")
        );
    }



    @Transactional
    public Cart addCartItem(@NonNull String email, @NonNull CartItemDto dto) {


            // Check if customer already has a cart
            Cart cart = cartRepository.findByCustomerEmail(email)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setCustomerEmail(email);
                        // If customer doesnt have cart a new one is created
                        return cartRepository.save(newCart);
                    });

            // Check if product exists
            ProductResponseDto productResponseDto = null;
            try {
                productResponseDto = productFacade.getProductById(dto.productId());
            } catch (SharedResourceNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            }

            // Check if product already exists in cart
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(dto.productId()))
                    .findFirst()
                    .orElse(null);
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProductId(dto.productId());
                cartItem.setQuantity(dto.quantity());
                cartItem.setUnitPrice(productResponseDto.price());
                cart.getItems().add(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + dto.quantity());
            }
            // Update item and overall cart totals
            cartItem.setPrices();
            updateCartTotal(cart);
            // Because this is annotated @Transactional the save method is done by Jpa
            // return response


            return cartRepository.save(cart);

    }

    @Transactional
    public Cart removeCartItem(@NonNull String email, @NonNull CartItemDto dto){

            Cart cart = cartRepository.findByCustomerEmail(email)
                    .orElseThrow(()-> new SharedResourceNotFoundException("Cart not found"));

            Predicate<CartItem> getProdId = (cartitem)-> cartitem.getProductId().equals(dto.productId());

            CartItem item = cart.getItems().stream().filter(
                    getProdId
            ).findFirst().orElseThrow(()-> new SharedResourceNotFoundException("CartItem not found"));

            if (dto.quantity() >= item.getQuantity()){
                cart.getItems().remove(item);
            }
            else {
                item.setQuantity(item.getQuantity() - dto.quantity());
            }

            updateCartTotal(cart);
            // May not need save since its annotated @Transactional

            return cart;

    }


    @Transactional
    public OrderResponseDto processCart(@NonNull String paymentMethod, @NonNull String email) {


            Cart cart = cartRepository.findByCustomerEmail(email).orElseThrow(()->
                    new SharedResourceNotFoundException("Cart not found"));

            List<CreateOrderItemDto> products = cart.getItems().stream().map(
                    (cartItem -> new CreateOrderItemDto(cartItem.getProductId(), cartItem.getQuantity()))
            ).toList();

            CreateOrderDto orderDto = new CreateOrderDto(
                    paymentMethod,
                    cart.getCustomerEmail(),
                    products
            );
            OrderResponseDto response = orderFacade.createOrder(orderDto);
            cart.getItems().clear();
            updateCartTotal(cart);
            return response;
    }
}
