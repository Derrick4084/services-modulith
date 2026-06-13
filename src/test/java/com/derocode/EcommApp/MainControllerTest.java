package com.derocode.EcommApp;


import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.services.ProductService;
import com.derocode.EcommApp.security.MainController;
import com.derocode.EcommApp.security.filters.JwtFilter;
import com.derocode.EcommApp.security.mappers.UserMapperImpl;
import com.derocode.EcommApp.security.repositories.UserRepository;
import com.derocode.EcommApp.security.services.AppUserService;
import com.derocode.EcommApp.security.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.math.BigDecimal;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(MainController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductFacade productFacade;

    @MockitoBean
    CustomerFacade customerFacade;

    @MockitoBean
    OrderFacade orderFacade;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    UserMapperImpl mapper;

    @MockitoBean
    AppUserService appUserService;

    @MockitoBean
    AuthenticationService authenticationService;

    @MockitoBean
    JwtFilter jwtFilter;

    @Test
    void shouldReturnProduct() throws Exception {

        when(productFacade.getProductById(70L)).thenReturn(new ProductResponseDto(
                70L,
            "Mechanical Keyboard 1",
            "Mechanical keyboard with RGB lighting",
            11.00,
            BigDecimal.valueOf(99.99),
            "Keyboards"
        ));

        mockMvc.perform(get("/api/product/70"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(70))
                .andExpect(jsonPath("$.name").value("Mechanical Keyboard 1"));

    }

}
