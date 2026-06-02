package com.derocode.EcommApp.customer.internals;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerMongoRepository extends MongoRepository<Customer,Long> {
    Optional<Customer> getCustomerByEmail(String email);

    boolean existsByEmail(String email);
}