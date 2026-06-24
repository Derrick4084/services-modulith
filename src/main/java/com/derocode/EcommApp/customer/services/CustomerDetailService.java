package com.derocode.EcommApp.customer.services;


import com.derocode.EcommApp.customer.repositories.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerDetailService implements UserDetailsService {

    private final CustomerMongoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.getCustomerByEmail(email).orElseThrow();
    }
}
