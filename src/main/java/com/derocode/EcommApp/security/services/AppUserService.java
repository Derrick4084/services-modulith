package com.derocode.EcommApp.security.services;



import com.derocode.EcommApp.exceptions.ResourceExistsException;
import com.derocode.EcommApp.exceptions.ResourceNotFoundException;
import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import com.derocode.EcommApp.security.api.UserResponseDto;
import com.derocode.EcommApp.security.models.Role;
import com.derocode.EcommApp.security.enums.RoleEnum;
import com.derocode.EcommApp.security.models.User;
import com.derocode.EcommApp.security.mappers.UserMapperImpl;
import com.derocode.EcommApp.security.repositories.RoleRepository;
import com.derocode.EcommApp.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapperImpl userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto findUser(@NonNull UserLoginRequestDto request) {
        Optional<User> user = userRepository.findByEmail(request.email());
        return user.map(userMapper::userToResponseDto).orElse(null);
    }

    public UserResponseDto addNewUser(@NonNull CreateUserDto request) {

        if(userRepository.existsByEmail(request.email())){
            throw new ResourceExistsException("User with this email " + request.email() + " already exists");
        }

        Role role = null;
        switch (request.role()) {
            case "USER" -> {
                Optional<Role> entity = roleRepository.getRoleByName(RoleEnum.USER);
                if (entity.isPresent()) {
                    role = entity.get();
                }
            }
            case "ADMIN" -> {
                Optional<Role> entity = roleRepository.getRoleByName(RoleEnum.ADMIN);
                if (entity.isPresent()) {
                    role = entity.get();
                }
            }
            case "OWNER" -> {
                Optional<Role> entity = roleRepository.getRoleByName(RoleEnum.OWNER);
                if (entity.isPresent()) {
                    role = entity.get();
                }
            }
            case null, default -> {
                return null;
            }
        }
        User user = userMapper.createUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.userToResponseDto(savedUser);


    }

    public String getData() {
        return "This is the data";
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
