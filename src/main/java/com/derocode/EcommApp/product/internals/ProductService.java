package com.derocode.EcommApp.product.internals;

import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.product.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final InventoryReservationRepository inventoryRepository;
//    private final OrderFacade orderFacade;

//    public void returnProducts(@NonNull List<OrderLineDto> requestedProducts) {
//
//        for(OrderLineDto ordto: requestedProducts) {
//            Product product = productRepository.getProductById(ordto.productId()).orElseThrow(
//                    ()-> new RuntimeException("No product id for this return")
//            );
//            product.setAvailableQuantity(product.getAvailableQuantity() + ordto.quantity());
//            productRepository.save(product);
//        }
//    }


    public ProductResponseDto getProductByName(@NonNull String name) {
        Optional<Product> product = productRepository.getProductByName(name);
        if (product.isPresent()) {
            return mapper.entityToResponse(product.get());
        }
        else {
            throw new RuntimeException("Product not found");
        }
    }


    public ProductResponseDto getProductById(@NonNull Long id) {
        Product product = productRepository.getProductById(id).orElseThrow(
                ()-> new RuntimeException("Product does not exists")
        );
        return mapper.entityToResponse(product);
    }


    public ProductResponseDto addNewProduct(@NonNull AddProductRequestDto addProductRequest) {

        Category category = categoryRepository.findByName(addProductRequest.category()).orElseThrow(()->
                new RuntimeException("There is no category with that name")
        );

        if (productRepository.getProductByName(addProductRequest.productName()).isEmpty()) {
            Product product = mapper.addProductRequestToEntity(addProductRequest);
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            return mapper.entityToResponse(savedProduct);
        }
        else {
            throw  new RuntimeException("Product with that name already exists");
        }
    }

    public List<ProductResponseDto> getAvailableProducts(@NonNull List<ProductOrderRequestDto> requestedProducts, Long orderId) {
        List<ProductResponseDto> availableProducts = new ArrayList<>();
        for (ProductOrderRequestDto productRequest : requestedProducts) {
            Product retProduct = productRepository.getProductById(productRequest.productId())
                    .orElseThrow(()-> new RuntimeException("Product not found"));
            if(retProduct.getAvailableQuantity() >= productRequest.quantity() && retProduct.getAvailableQuantity() != 0) {
                double var = retProduct.getAvailableQuantity() - productRequest.quantity();
                availableProducts.add(
                        new ProductResponseDto(
                                retProduct.getId(),
                                retProduct.getName(),
                                retProduct.getDescription(),
                                productRequest.quantity(),
                                retProduct.getPrice(),
                                retProduct.getCategory().getName()));
                retProduct.setAvailableQuantity(var);

                productRepository.save(retProduct);

                InventoryReservation reservation = InventoryReservation.builder()
                                .orderId(orderId)
                                        .productId(retProduct.getId())
                                                .quantity(productRequest.quantity())
                                                        .build();
                inventoryRepository.save(reservation);


            }
        }
        return availableProducts;
    }



    @Transactional
    public void handlePaymentEvent(@NonNull PaymentEventDto event) {

        if (Objects.equals(event.status(), "DENIED")) {
            List<InventoryReservation> items = inventoryRepository.findByOrderId(event.orderId());

            for(InventoryReservation ir: items){

                Product product = productRepository.getProductById(ir.getProductId()).orElseThrow(
                        ()-> new RuntimeException("Product not found matching return item")
                );

                product.setAvailableQuantity(product.getAvailableQuantity() + ir.getQuantity());
                productRepository.save(product);

            }




        }

    }
}




//}
