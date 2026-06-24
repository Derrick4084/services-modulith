package com.derocode.EcommApp.customer.models;


import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer implements UserDetails {
    @Transient
    public static final String CUSTOMER_SEQUENCE = "customer_sequence";

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private List<Address> addresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
                "ROLE_CUSTOMER"
        );
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
