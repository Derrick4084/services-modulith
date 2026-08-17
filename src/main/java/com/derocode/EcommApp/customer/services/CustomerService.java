package com.derocode.EcommApp.customer.services;


import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.customer.mapper.CustomerMapper;
import com.derocode.EcommApp.customer.models.Customer;
import com.derocode.EcommApp.customer.models.CustomerDatabaseSequence;
import com.derocode.EcommApp.customer.repositories.CustomerMongoRepository;
import com.derocode.EcommApp.exceptions.SharedResourceExistsException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CustomerService {

    private final CustomerMongoRepository repository;
    private final MongoOperations mongoOperations;
    private final CustomerMapper mapper;
    private final PasswordEncoder encoder;

    public CustomerService(
            CustomerMongoRepository repository,
            @Qualifier("customerMongoTemplate") MongoOperations mongoOperations,
            CustomerMapper mapper,
            PasswordEncoder encoder
    ) {
        this.repository = repository;
        this.mongoOperations = mongoOperations;
        this.mapper = mapper;
        this.encoder = encoder;
    }


    public long generateSequence(String seqName) {
        CustomerDatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                CustomerDatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }


    @Cacheable(value = "customersByEmail", key = "#email") // Caches the result only if the ID is greater than 10.
    public CustomerResponseDto getCustomerByEmail(String email) {
        Customer customer = repository.getCustomerByEmail(email).orElseThrow(
                ()-> new SharedResourceNotFoundException("Customer not found")
        );

        return mapper.entityToResponse(customer);

    }

    @CacheEvict(value = "pagedCustomers", allEntries = true)
    public Customer addNewCustomer(@NonNull AddCustomerRequestDto addCustomerRequestDto) {
        if(repository.existsByEmail(addCustomerRequestDto.email())){
            throw new SharedResourceExistsException("Customer with with this email already exists");
        }
        Customer customer = mapper.requestToCustomer(addCustomerRequestDto);
        customer.setId(generateSequence(Customer.CUSTOMER_SEQUENCE));
        customer.setPassword(encoder.encode(addCustomerRequestDto.password()));
        return repository.save(customer);
    }

    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }


    @Cacheable(value = "pagedCustomers", key = "'pagedCustomers:' + #page + ':' + (#size > 20 ? 20 : #size)")
    public Page<Customer> getAll(int page, int size){

        size = Math.max(size, 20);

        Pageable pageable = PageRequest.of(page,size);

        return repository.findAll(pageable);

    }





}
