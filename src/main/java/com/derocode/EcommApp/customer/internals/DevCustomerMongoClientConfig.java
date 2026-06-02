package com.derocode.EcommApp.customer.internals;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Profile("dev")
@Configuration
@EnableMongoRepositories(
        basePackages = "com.derocode.EcommApp.customer.internals",
        mongoTemplateRef = "customerMongoTemplate"
)
public class DevCustomerMongoClientConfig {
    @Bean(name = "customerMongoClient")
    public MongoClient mongoClient() {

        MongoCredential credential =
                MongoCredential.createScramSha256Credential(
                        "dero",
                        "admin",
                        "dero".toCharArray()
                );

        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(
                                        List.of(
                                                new ServerAddress(
                                                        "localhost",
                                                        27017
                                                )
                                        )
                                )
                        )
                        .credential(credential)
                        .build()
        );
    }

    @Bean(name = "customerMongoDatabaseFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("customerMongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient,
                "customers"
        );
    }

    @Bean("customerMappingMongoConverter")
    public MappingMongoConverter customerMappingMongoConverter(
            @Qualifier("customerMongoDatabaseFactory")
            MongoDatabaseFactory factory,
            MongoMappingContext context,
            MongoCustomConversions conversions
    ) {

        MappingMongoConverter converter =
                new MappingMongoConverter(
                        new DefaultDbRefResolver(factory),
                        context
                );

        converter.setCustomConversions(conversions);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }


    @Bean(name = "customerMongoTemplate")
    public MongoTemplate customerMongoTemplate(@Qualifier("customerMongoDatabaseFactory") MongoDatabaseFactory factory,
            @Qualifier("customerMappingMongoConverter") MappingMongoConverter converter) {
        return new MongoTemplate(factory, converter);
    }

    @Bean
    public ApplicationRunner initIndexes(@Qualifier("customerMongoTemplate") MongoTemplate mongoTemplate) {
        return args -> {
            mongoTemplate.indexOps(Customer.class).createIndex(
                    new Index()
                            .on("email", Sort.Direction.ASC)
                            .unique()
            );
        };
    }
}
