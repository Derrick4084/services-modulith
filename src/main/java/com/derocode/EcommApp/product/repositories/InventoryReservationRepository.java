package com.derocode.EcommApp.product.repositories;

import com.derocode.EcommApp.product.models.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation,Long> {

    List<InventoryReservation> findByOrderId(Long orderId);

}
