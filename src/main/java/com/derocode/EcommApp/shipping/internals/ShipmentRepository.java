package com.derocode.EcommApp.shipping.internals;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

}
