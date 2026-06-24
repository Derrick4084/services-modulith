package com.derocode.EcommApp.security.filters;



import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.security.services.AppUserDetailService;
import com.derocode.EcommApp.jwt.SharedJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final SharedJwtService jwtService;
    private final ApplicationContext context;

    public JwtFilter(SharedJwtService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/actuator/health") || path.equals("/customer/authenticate") || path.equals("/user/authenticate")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Get Authorization header from request
            String authHeader = request.getHeader("Authorization");
            String token = null;

            // Check header for Bearer key in Auth header
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                // Extract toke from header value
                token = authHeader.substring(7);

                // Extract username from token. in this case email is used for authentication
                String usernameFromToken = jwtService.extractUserName(token);
                String userType = jwtService.extractUserType(token);

                UserDetails userDetail = null;
                if(Objects.equals(userType,"customer")) {
                    userDetail = context.getBean(CustomerFacade.class).loadCustomerByEmail(usernameFromToken);
                }
                else {
                    userDetail = context.getBean(AppUserDetailService.class).loadUserByUsername(usernameFromToken);
                }


                // Get user details from database


                // Check if token is valid and if username is valid and extract roles from token
                if(jwtService.validateTokenUsingUserDetails(token,userDetail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Claims claims = jwtService.extractAllClaims(token);
                    List<String> roles = jwtService.extractRoles(claims);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                            .toList();

                    // Create UsernamePasswordAuthenticationToken with information if authentication passed
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),null, authorities);

                    // Add roles to security context of token then return to client
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

