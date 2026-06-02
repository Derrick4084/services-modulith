package com.derocode.EcommApp.customer.internals;



import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.AddCustomerRequestDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CustomerFacadeImpl implements CustomerFacade {

    private final CustomerMongoRepository repository;
    private final MongoOperations mongoOperations;
    private final CustomerMapper mapper;

    public CustomerFacadeImpl(CustomerMongoRepository repository, @Qualifier("customerMongoTemplate")MongoOperations mongoOperations, CustomerMapper mapper) {
        this.repository = repository;
        this.mongoOperations = mongoOperations;
        this.mapper = mapper;
    }

    public long generateSequence(String seqName) {
        CustomerDatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                CustomerDatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }


    @Override
    public CustomerResponseDto getCustomerByEmail(String email) {
        Customer customer = repository.getCustomerByEmail(email).orElseThrow(
                ()-> new RuntimeException("Customer not found")
        );
        return mapper.entityToResponseDto(customer);

    }

    @Override
    public CustomerResponseDto addNewCustomer(@NonNull AddCustomerRequestDto addCustomerRequestDto) {
        if(repository.existsByEmail(addCustomerRequestDto.email())){
            throw new RuntimeException("Customer with with this email already exists");
        }
        Customer customer = mapper.addCustomerRequestToEntity(addCustomerRequestDto);
        customer.setId(generateSequence(Customer.CUSTOMER_SEQUENCE));
        Customer savedCustomer = repository.save(customer);
        return mapper.entityToResponseDto(savedCustomer);
    }
}
