package com.derocode.EcommApp.customer.internals;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.AddressResponseDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import org.jspecify.annotations.NonNull;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomerMapper {

    default CustomerResponseDto entityToResponseDto(@NonNull Customer customer) {
        List<AddressResponseDto> addresses = customer.getAddresses()
                .stream().map(
                        address -> new AddressResponseDto(
                                address.getHouseNumber(),
                                address.getStreet(),
                                address.getCity(),
                                address.getState(),
                                address.getZipCode()
                        )
                ).toList();
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                addresses
        );

    }


    default Customer addCustomerRequestToEntity(@NonNull AddCustomerRequestDto request) {
        List<Address> addresses = request.addresses()
                .stream().map(
                        address -> Address.builder()
                                .houseNumber(address.houseNumber())
                                .street(address.street())
                                .city(address.city())
                                .state(address.state())
                                .zipCode(address.zipCode())
                                .build()
                ).toList();
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .addresses(addresses)
                .build();

    }
}