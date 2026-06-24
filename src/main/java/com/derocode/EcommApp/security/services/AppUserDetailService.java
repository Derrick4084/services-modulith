package com.derocode.EcommApp.security.services;



import com.derocode.EcommApp.exceptions.SharedResourceExistsException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import com.derocode.EcommApp.security.models.Role;
import com.derocode.EcommApp.security.enums.RoleEnum;
import com.derocode.EcommApp.security.models.User;
import com.derocode.EcommApp.security.mappers.UserMapperImpl;
import com.derocode.EcommApp.security.repositories.RoleRepository;
import com.derocode.EcommApp.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder encoder;


    public User createAdmin(@NonNull CreateUserDto request) {
        if(userRepository.existsByEmail(request.email())){
            throw new SharedResourceExistsException("User with this email " + request.email() + " already exists");
        }
        Role role = roleRepository.getRoleByName(RoleEnum.ADMIN).orElseThrow(()->
                new SharedResourceNotFoundException("Role not found"));
        User user = userMapper.createUserDtoToUser(request);
        user.setPassword(encoder.encode(request.password()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User createUser(@NonNull CreateUserDto request) {
        if(userRepository.existsByEmail(request.email())){
            throw new SharedResourceExistsException("User with this email " + request.email() + " already exists");
        }
        Role role = roleRepository.getRoleByName(RoleEnum.USER).orElseThrow(()->
                new SharedResourceNotFoundException("Role not found"));
        User user = userMapper.createUserDtoToUser(request);
        user.setPassword(encoder.encode(request.password()));
        user.setRole(role);
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User with this email" + email + " not found");
        }
        return user.get();
    }
}
