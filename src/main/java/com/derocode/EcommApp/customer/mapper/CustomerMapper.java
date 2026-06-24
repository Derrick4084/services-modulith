package com.derocode.EcommApp.customer.mapper;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.AddressResponseDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.customer.api.CustomerAddressRequestDto;
import com.derocode.EcommApp.customer.models.Address;
import com.derocode.EcommApp.customer.models.Customer;
import org.jspecify.annotations.NonNull;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CustomerMapper {


    @Mapping(target = "addresses", source = "addresses")
    CustomerResponseDto entityToResponse(Customer customer);

    AddressResponseDto entityToAddressResponse(Address address);

    @Mapping(target = "addresses", source = "addresses")
    Customer requestToCustomer(AddCustomerRequestDto request);

    Address requestToAddress(CustomerAddressRequestDto request);
}