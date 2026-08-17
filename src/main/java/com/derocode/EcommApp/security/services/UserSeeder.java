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

    private Role getRole(RoleEnum roleEnum){
        return roleRepository.getRoleByName(roleEnum).orElseGet(
                () -> {
                    log.warn( roleEnum.name() +  " role not found. Falling back to USER role");
                    return roleRepository.getRoleByName(RoleEnum.USER)
                            .orElseThrow(() ->
                                    new IllegalStateException("USER role not found"));
                }
        );
    }


    private void saveUser(User user) {
        try {
            userRepository.save(user);
            log.info(user.getFirstName()  +  " seeded successfully");
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            log.warn("There was a problem saving " + user.getFirstName() + ": "  + e.getMessage());
        }
    }

    private void loadUser () {
        if(userRepository.findByEmail("admin@example.com").isEmpty()) {

            User adminUser = User.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@example.com")
                    .password(encoder.encode("abc12345"))
                    .role(getRole(RoleEnum.ADMIN))
                    .build();
            saveUser(adminUser);
        }
        else {
            log.info("Admin user already exists");
        }

        if(userRepository.findByEmail("agent@example.com").isEmpty()) {
            User aiUser = User.builder()
                    .firstName("Ai")
                    .lastName("Agent")
                    .email("agent@example.com")
                    .password(encoder.encode("abc12345"))
                    .role(getRole(RoleEnum.AI))
                    .build();
            saveUser(aiUser);
        }
        else {
            log.info("Ai agent already exists");
        }
    }
}
