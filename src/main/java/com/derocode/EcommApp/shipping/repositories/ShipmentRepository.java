package com.derocode.EcommApp.shipping.repositories;


import com.derocode.EcommApp.shipping.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

    Boolean existsByOrderId(Long orderId);

}
