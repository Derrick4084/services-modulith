package com.derocode.EcommApp.security;

import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.api.UserLoginRequestDto;
import com.derocode.EcommApp.security.api.UserResponseDto;
import com.derocode.EcommApp.security.services.AppUserDetailService;
import com.derocode.EcommApp.security.services.UserAuthenticationService;
import com.derocode.EcommApp.security.models.User;
import com.derocode.EcommApp.security.mappers.UserMapperImpl;
import com.derocode.EcommApp.security.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;
    private final UserMapperImpl mapper;
    private final AppUserDetailService appUserService;
    private final UserAuthenticationService authenticationService;

    @PostMapping("/user/authenticate")
    public ResponseEntity<String> userLogin(@Valid @RequestBody @NonNull UserLoginRequestDto loginRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/user/{email}")
    public ResponseEntity<Object> findUser(@PathVariable @NonNull String email) {
        if(userRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User response = userRepository.findByEmail(email).get();
        return ResponseEntity.ok(mapper.userToResponseDto(response));
    }


    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    @PostMapping("/user/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDto request){
        try {
            User response = appUserService.createUser(request);
            return ResponseEntity.ok(mapper.userToResponseDto(response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('OWNER')")
    @PostMapping("/admin/create")
    public ResponseEntity<Object> createAdmin(@Valid @RequestBody CreateUserDto request){
        try {
            User response = appUserService.createAdmin(request);

            return ResponseEntity.ok(mapper.userToResponseDto(response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}





