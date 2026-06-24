package com.derocode.EcommApp.product.services;


import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.product.models.InventoryReservation;
import com.derocode.EcommApp.product.models.Product;
import com.derocode.EcommApp.product.repositories.InventoryReservationRepository;
import com.derocode.EcommApp.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class ProductEventHandlerService {

    private final ProductRepository productRepository;
    private final InventoryReservationRepository inventoryRepository;

    @Transactional
    public void handlePaymentEvent(@NonNull SharedPaymentEventDto event) {

        if (Objects.equals(event.status(), "DENIED")) {
            List<InventoryReservation> items = inventoryRepository.findByOrderId(event.orderId());

            for(InventoryReservation ir: items){

                Product product = productRepository.getProductById(ir.getProductId()).orElseThrow(
                        ()-> new SharedResourceNotFoundException("Product not found matching return item")
                );

                product.setAvailableQuantity(product.getAvailableQuantity() + ir.getQuantity());
                productRepository.save(product);

            }

        }

    }

}
