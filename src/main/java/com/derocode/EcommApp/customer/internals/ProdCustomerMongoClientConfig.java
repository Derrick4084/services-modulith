package com.derocode.EcommApp.customer.internals;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Profile("prod")
@Configuration
@EnableMongoRepositories(
        basePackages = "com.derocode.EcommApp.customer.internals",
        mongoTemplateRef = "customerMongoTemplate"
)
public class ProdCustomerMongoClientConfig {

    @Value("${documentdb.password}")
    private String docDbPassword;

    @Value("${documentdb.username}")
    private String docDbUserName;

    @Value("${documentdb.documentdb_endpoint}")
    private String docDbHost;

    @Value("${documentdb.port}")
    private int docDbPort;

    @Bean(name = "customerMongoClient")
    public MongoClient mongoClient() throws Exception{

        MongoCredential credential = MongoCredential.createScramSha1Credential(
                docDbUserName,
                "admin",
                docDbPassword.toCharArray()
        );


        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

        try (InputStream is = new FileInputStream("/app/certs/rds-combined-ca-bundle.pem")) {
            trustStore.load(null,null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certs = cf.generateCertificates(is);
            int i = 0;
            for (Certificate cert : certs) {
                trustStore.setCertificateEntry("rds-cert-" + i++, cert);
            }
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(List.of(
                                        new ServerAddress(docDbHost, docDbPort)))
                                .requiredReplicaSetName("rs0")
                )
                .applyToSslSettings(builder -> {
                    builder.enabled(true);
                    builder.context(sslContext);
                    builder.invalidHostNameAllowed(true);
                })
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(10, TimeUnit.SECONDS))
                .retryWrites(false)
                .credential(credential)
                .build();

        return MongoClients.create(mongoClientSettings);
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
