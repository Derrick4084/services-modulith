package com.derocode.EcommApp;


import com.derocode.EcommApp.cart.api.CartItemDto;
import com.derocode.EcommApp.cart.api.CartResponseDto;
import com.derocode.EcommApp.cart.mapper.CartMapperImpl;
import com.derocode.EcommApp.cart.models.Cart;
import com.derocode.EcommApp.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.springframework.security.core.Authentication;


@SpringBootTest
@Transactional
public class CartAddItemTest {

    @Autowired
    CartService cartService;

    @Autowired
    private CartMapperImpl cartMapper;

    @MockitoBean
    Authentication authentication;

    @Test
    void shouldReturnCartResponseDto(){

        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.isAuthenticated()).thenReturn(true);

        CartItemDto itemDto = new CartItemDto(1L, 2.0);
        Cart cart = cartService.addCartItem("test@example.com", itemDto);

        CartResponseDto responseDto = cartMapper.cartToDto(cart);

        assertThat(responseDto.totalAmount().doubleValue()).isEqualByComparingTo(199.98);

        assertThat(responseDto.items().getFirst().name()).isEqualTo("Mechanical Keyboard 1");
    }


}
