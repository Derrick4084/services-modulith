package com.derocode.EcommApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class
})
//@EnableCaching
public class EcommAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommAppApplication.class, args);

	}

}



