package com.derocode.EcommApp.security.services;


import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String authenticate(@NonNull UserLoginRequestDto request) {
        Authentication auth =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.email(),
                        request.password()
                );

        Authentication result =
                authenticationManager.authenticate(auth);

        return jwtService.generateTokenWithRoles(result);
    }

}
