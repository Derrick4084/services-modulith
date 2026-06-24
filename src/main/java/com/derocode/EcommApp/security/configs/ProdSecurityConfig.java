package com.derocode.EcommApp.security.configs;


import com.derocode.EcommApp.security.filters.JwtFilter;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
@Profile("prod")
public class ProdSecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/customer/authenticate").permitAll()
                        .requestMatchers("/user/authenticate").permitAll()
                        .requestMatchers("/actuator/health").permitAll()

                        // Customer viewing
                        .requestMatchers(HttpMethod.GET, "/customer/**").hasAnyRole("CUSTOMER", "USER")

                        // Customer management
                        .requestMatchers(HttpMethod.POST, "/customer/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/customer/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/customer/**").hasAnyRole("ADMIN", "OWNER")

                        // Product viewing
                        .requestMatchers(HttpMethod.GET, "/product/**").hasAnyRole("CUSTOMER", "USER","ADMIN")
                        // Product management
                        .requestMatchers(HttpMethod.POST, "/product/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAnyRole("ADMIN", "OWNER")

                        // Cart
                        .requestMatchers("/cart/**").hasAnyRole("CUSTOMER", "USER")

                        // Orders
                        .requestMatchers(HttpMethod.POST, "/order/find/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST, "/order/create").hasRole("CUSTOMER")

                        // Shipment
                        .requestMatchers("/shipment/**").hasAnyRole("ADMIN","USER")

                        // Admin area
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "OWNER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }
}







