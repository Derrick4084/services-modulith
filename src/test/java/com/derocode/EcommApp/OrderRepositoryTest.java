package com.derocode.EcommApp;


import com.derocode.EcommApp.enums.SharedOrderStatus;
import com.derocode.EcommApp.enums.SharedPaymentMethod;
import com.derocode.EcommApp.order.models.Order;
import com.derocode.EcommApp.order.models.OrderLine;
import com.derocode.EcommApp.order.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    @Test
    void shouldSaveAndRetrieveOrder() {

        List<OrderLine> orderLines = new ArrayList<>();

        Order order = Order.builder()
                .customerEmail("test@test.com")
                .reference("ORD-123")
                .totalAmount(BigDecimal.valueOf(20.00))
                .paymentMethod(SharedPaymentMethod.DISCOVER_CARD)
                .status(SharedOrderStatus.CREATED)
                .orderDate(LocalDateTime.now())
                .build();

        OrderLine ol = OrderLine.builder()
                .price(BigDecimal.valueOf(10.00))
                .productId(20L)
                .quantity(2.0)
                .lineTotal(BigDecimal.valueOf(20.00))
                .order(order)
                .build();

        orderLines.add(ol);

        order.setOrderLines(orderLines);

        Order saved = repository.save(order);

        Optional<Order> found =
                repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCustomerEmail())
                .isEqualTo("test@test.com");
    }






}
