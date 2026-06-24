package com.derocode.EcommApp.security.services;


import com.derocode.EcommApp.jwt.SharedJwtService;
import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final SharedJwtService jwtService;

    public UserAuthenticationService(AuthenticationManager authenticationManager, SharedJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String authenticateUser(@NonNull UserLoginRequestDto request) {
        Authentication auth =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.email(),
                        request.password()
                );

        Authentication result =
                authenticationManager.authenticate(auth);

        return jwtService.generateTokenWithRoles(result, "user");
    }




}
