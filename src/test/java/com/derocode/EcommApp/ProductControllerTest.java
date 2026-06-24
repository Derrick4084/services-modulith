package com.derocode.EcommApp;


import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.jwt.SharedJwtService;
import com.derocode.EcommApp.product.ProductController;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.mappers.ProductMapperImpl;
import com.derocode.EcommApp.product.models.Category;
import com.derocode.EcommApp.product.models.Product;
import com.derocode.EcommApp.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductMapperImpl productMapper;

    @MockitoBean
    SharedJwtService jwtService;

    @MockitoBean
    CustomerFacade customerFacade;

    @MockitoBean
    ProductService productService;


    @Test
    void shouldReturnProduct() throws Exception {

        when(productMapper.entityToResponse(productService.getProductById(70L))).thenReturn(new ProductResponseDto(
                70L,
            "Mechanical Keyboard 1",
            "Mechanical keyboard with RGB lighting",
            11.00,
            BigDecimal.valueOf(99.99),
            "Keyboards"
        ));

        mockMvc.perform(get("/product/70"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(70))
                .andExpect(jsonPath("$.name").value("Mechanical Keyboard 1"));

    }

}
