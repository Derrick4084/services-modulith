package com.derocode.EcommApp.shipping.mappers;

import com.derocode.EcommApp.events.SharedShipmentEventDto;
import com.derocode.EcommApp.shipping.ShipmentResponseDto;
import com.derocode.EcommApp.shipping.models.Shipment;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ShippingMapper {

    @Mapping(target = "shipmentId", source = "id")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "trackingNumber", source = "trackingNumber")
    @Mapping(target = "carrier", source = "carrier")
    @Mapping(target = "estimatedDeliveryDate", source = "estimatedDeliveryDate")
    SharedShipmentEventDto entityToEvent(Shipment shipment);



    ShipmentResponseDto entityToResponse(Shipment shipment);
}
