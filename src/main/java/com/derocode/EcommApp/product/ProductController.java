package com.derocode.EcommApp.product;


import com.derocode.EcommApp.product.api.AddProductRequestDto;
import com.derocode.EcommApp.product.mappers.ProductMapperImpl;
import com.derocode.EcommApp.product.models.Product;
import com.derocode.EcommApp.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapperImpl productMapper;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/product/add")
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody AddProductRequestDto request){
        Product product = productService.addNewProduct(request);
        return ResponseEntity.ok(productMapper.entityToResponse(product));
    }

    @GetMapping("/product/all")
    public ResponseEntity<Page<ProductResponseDto>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ProductResponseDto> response = productService.getAll(page, size).map(productMapper::entityToResponse);
        return ResponseEntity.ok(response);
    }
}
