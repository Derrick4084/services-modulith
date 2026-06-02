package com.derocode.EcommApp.order.internals;



import com.derocode.EcommApp.product.ProductResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderUtils {

    public String generateRef() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ODR-" + datePart + "-" + randomPart;
    }

    public @NonNull Double productsTotalAmount(@NonNull List<OrderLine> availableProducts){
        List<Double> total = new ArrayList<>();
        for (OrderLine product : availableProducts) {
            total.add(product.getLineTotal().doubleValue());
        }
        return total.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public String generateEventId() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public List<OrderLine> getOrderLines(@NonNull List<ProductResponseDto> prodResponseList, Order order) {

        return prodResponseList.stream()
                .map(prrd ->  new OrderLine(
                        null,
                        order,
                        prrd.id(),
                        prrd.name(),
                        prrd.price(),
                        prrd.availableQuantity(),
                        BigDecimal.valueOf(prrd.availableQuantity() * prrd.price().doubleValue())
                ))
                .toList();
    }






}





//




//    public @NonNull List<OrderLine> prepOrderLines(@NonNull CreateOrderHttpRequest request, Order order){
//        List<OrderProductHttpRequest> requestedProducts = request.getProducts();
//        List<OrderLine> orderLines = new ArrayList<>();
//        for (OrderProductHttpRequest createOrderProductRequest : requestedProducts) {
//            try {
//                ProductResponse productResponse = productGrpcClient.getProductById(ProductRequest.newBuilder()
//                        .setId(longToInt(createOrderProductRequest.getProductId()))
//                        .build()
//                );
//                if (productResponse.getAvailableQuantity() >= createOrderProductRequest.getQuantity()) {
//                    OrderLine orderLine = lombokMapper.toOrderLine(productResponse, createOrderProductRequest);
//                    orderLine.setOrder(order);
//                    orderLines.add(orderLine);
//                }
//            } catch (StatusRuntimeException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        UpdateProductQtyBulkRequest bulk = lombokMapper.orderLineToUpdateBulkRequest(orderLines);
//        try {
//            productGrpcClient.getProductsFromInventory(bulk);
//            return orderLines;
//        } catch (StatusRuntimeException e) {
//            throw new RuntimeException(e);
//        }



