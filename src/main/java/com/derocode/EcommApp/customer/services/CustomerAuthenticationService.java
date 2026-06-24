package com.derocode.EcommApp.customer.services;


import com.derocode.EcommApp.customer.api.CustomerLoginRequestDto;
import com.derocode.EcommApp.jwt.SharedJwtService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomerAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final SharedJwtService jwtService;

    public CustomerAuthenticationService(@Qualifier("customerAuthenticationManager") AuthenticationManager authenticationManager, SharedJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String authenticateCustomer(@NonNull CustomerLoginRequestDto loginRequestDto) {

        Authentication auth =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginRequestDto.email(),
                        loginRequestDto.password()
                );

        Authentication authentication = authenticationManager.authenticate(auth);

        return jwtService.generateTokenWithRoles(authentication, "customer");

    }




}
