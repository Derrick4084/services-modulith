package com.derocode.EcommApp.shipping.services;

import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.shipping.ShipmentResponseDto;
//import com.derocode.EcommApp.shipping.mappers.ShippingMapperImpl;
import com.derocode.EcommApp.shipping.models.Shipment;
import com.derocode.EcommApp.shipping.repositories.ShipmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ShipmentService {

    private final ShipmentRepository repository;

    public Shipment getShipment(Long id) {

        Shipment shipment = null;
        if(repository.existsByOrderId(id)) {
            return repository.getByOrderId(id);
        }
        else {
            throw new SharedResourceNotFoundException("Shipment not found for this order");
        }









    }

}
