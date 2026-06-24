package com.derocode.EcommApp.customer.configs;


import com.derocode.EcommApp.customer.services.CustomerDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomerAuthManager {
    @Bean(name = "customerAuthenticationManager")
    public AuthenticationManager customerAuthenticationManager(CustomerDetailService customerUserService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customerUserService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }



}
