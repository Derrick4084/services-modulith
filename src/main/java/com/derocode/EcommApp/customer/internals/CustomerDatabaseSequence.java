package com.derocode.EcommApp.customer.internals;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customer_sequences")
public class CustomerDatabaseSequence {
    @Id
    private String id;

    private long seq;
}
