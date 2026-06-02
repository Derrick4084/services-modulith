package com.derocode.EcommApp.order.internals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderLines WHERE o.Id = :id")
    Optional<Order> findByIdWithOrderLines(Long id);

    Optional<Order> findByReference(String reference);
}
