package com.derocode.EcommApp.shipping;

import com.derocode.EcommApp.shipping.mappers.ShippingMapper;
import com.derocode.EcommApp.shipping.mappers.ShippingMapperImpl;
import com.derocode.EcommApp.shipping.models.Shipment;
import com.derocode.EcommApp.shipping.services.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ShippingController {

    private final ShipmentService shipmentService;
    private final ShippingMapperImpl mapper;



    @PostMapping("/shipment/{id}")
    public ResponseEntity<ShipmentResponseDto> getShipment(@PathVariable Long id) {

        Shipment shipment = shipmentService.getShipment(id);

        return ResponseEntity.ok().body(mapper.entityToResponse(shipment));

    }
}

