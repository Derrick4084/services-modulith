package com.derocode.EcommApp.cache;

import com.derocode.EcommApp.cart.api.CartResponseDto;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class SharedRedisConfig {


    @Bean(name = "shoppingCartConfig")
    public RedisCacheConfiguration shoppingCartConfig() {
        return createRedisConfig(CartResponseDto.class, Duration.ofMinutes(10));
    }

    @Bean(name = "customerConfig")
    public RedisCacheConfiguration customerConfig() {
        return createRedisConfig(CustomerResponseDto.class, Duration.ofMinutes(10));
    }

    @Bean(name = "productConfig")
    public RedisCacheConfiguration productConfig() {
        return createRedisConfig(ProductResponseDto.class, Duration.ofMinutes(10));
    }


    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory connectionFactory,
            @Qualifier("shoppingCartConfig") RedisCacheConfiguration shoppingCartConf,
            @Qualifier("productConfig") RedisCacheConfiguration productConf,
            @Qualifier("customerConfig") RedisCacheConfiguration customerConf
    ) {

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10))
                        .disableCachingNullValues()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer())
                        ))
                .withCacheConfiguration("productsById", productConf)
                .withCacheConfiguration("customersByEmail", customerConf)
                .withCacheConfiguration("cartByEmail", shoppingCartConf)
                .build();

    }



    private <T> RedisCacheConfiguration createRedisConfig(Class<T> dtoClass, Duration ttl){

        JacksonJsonRedisSerializer<T> serializer = new JacksonJsonRedisSerializer<>(dtoClass);
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(serializer)
                );

    }

}
