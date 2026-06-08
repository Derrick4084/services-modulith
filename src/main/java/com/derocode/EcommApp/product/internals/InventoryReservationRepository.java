package com.derocode.EcommApp.product.internals;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {

    List<InventoryReservation> findByOrderId(Long orderId);

}
