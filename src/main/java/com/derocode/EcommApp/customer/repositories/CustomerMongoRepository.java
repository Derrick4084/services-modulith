package com.derocode.EcommApp.customer.repositories;

import com.derocode.EcommApp.customer.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerMongoRepository extends MongoRepository<Customer,Long> {
    Optional<Customer> getCustomerByEmail(String email);

    Boolean existsByEmail(String email);

    void deleteByEmail(String email);
}