package com.derocode.EcommApp.security.services;


import com.derocode.EcommApp.security.enums.RoleEnum;
import com.derocode.EcommApp.security.models.Role;
import com.derocode.EcommApp.security.models.User;
import com.derocode.EcommApp.security.repositories.RoleRepository;
import com.derocode.EcommApp.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        loadUser();


    }

    private void loadUser () {

        if(userRepository.findByEmail("admin@example.com").isPresent()) {
            log.info("Admin user already exists");
            return;
        }
        Role role = roleRepository.getRoleByName(RoleEnum.ADMIN).orElseGet(
                () -> {
                    log.warn("ADMIN role not found. Falling back to USER role");
                    return roleRepository.getRoleByName(RoleEnum.USER)
                            .orElseThrow(() ->
                                    new IllegalStateException("USER role not found"));
                }
        );
        User user = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .email("admin@example.com")
                .password(encoder.encode("abc123"))
                .role(role)
                .build();

        try {
            userRepository.save(user);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            log.warn("There was a problem saving Admin user" + e.getMessage());
        }

        log.info("Admin user seeded successfully");
    }
}
