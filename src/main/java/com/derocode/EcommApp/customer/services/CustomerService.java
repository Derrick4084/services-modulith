package com.derocode.EcommApp.customer.services;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.customer.mapper.CustomerMapper;
import com.derocode.EcommApp.customer.models.Customer;
import com.derocode.EcommApp.customer.models.CustomerDatabaseSequence;
import com.derocode.EcommApp.customer.repositories.CustomerMongoRepository;
import com.derocode.EcommApp.exceptions.ResourceExistsException;
import com.derocode.EcommApp.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerMongoRepository repository;
    private final MongoOperations mongoOperations;
    private final CustomerMapper mapper;

    public long generateSequence(String seqName) {
        CustomerDatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                CustomerDatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public CustomerResponseDto getCustomerByEmail(String email) {
        Customer customer = repository.getCustomerByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found")
        );
        return mapper.entityToResponseDto(customer);

    }

    public CustomerResponseDto addNewCustomer(@NonNull AddCustomerRequestDto addCustomerRequestDto) {
        if(repository.existsByEmail(addCustomerRequestDto.email())){
            throw new ResourceExistsException("Customer with with this email already exists");
        }
        Customer customer = mapper.addCustomerRequestToEntity(addCustomerRequestDto);
        customer.setId(generateSequence(Customer.CUSTOMER_SEQUENCE));
        Customer savedCustomer = repository.save(customer);
        return mapper.entityToResponseDto(savedCustomer);
    }

    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }



}
