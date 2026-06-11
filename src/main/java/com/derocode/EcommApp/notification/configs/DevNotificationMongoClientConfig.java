package com.derocode.EcommApp.notification.configs;


import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Profile("!prod")
@Configuration
@Slf4j
@EnableMongoRepositories(
        basePackages = "com.derocode.EcommApp.notification.repository",
        mongoTemplateRef = "notificationMongoTemplate"
)
public class DevNotificationMongoClientConfig {

    @Bean(name = "notificationMongoClient")
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

    @Primary
    @Bean(name = "notificationMongoDatabaseFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("notificationMongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient,
                "notifications"
        );
    }

    @Bean("notificationMappingMongoConverter")
    public MappingMongoConverter notificationMappingMongoConverter(
            @Qualifier("notificationMongoDatabaseFactory")
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


    @Primary
    @Bean(name = "notificationMongoTemplate")
    public MongoTemplate notificationMongoTemplate(
            @Qualifier("notificationMongoDatabaseFactory")
            MongoDatabaseFactory factory,
            @Qualifier("notificationMappingMongoConverter")
            MappingMongoConverter converter) {

        return new MongoTemplate(factory, converter);
    }


}
