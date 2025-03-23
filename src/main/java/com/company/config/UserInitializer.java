package com.company.config;

import com.company.enums.Roles;
import com.company.model.entity.UserEntity;
import com.company.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("super-admin").isEmpty()) {
            log.info("Creating super-admin user");
            userRepository.save(getUser());
        }
    }

    private UserEntity getUser() {
        UserEntity user = new UserEntity();
        user.setUsername("super-admin");
        user.setPassword(passwordEncoder.encode("1q2w3e4r5t"));
        user.setState(Boolean.TRUE);
        user.setRoles(Roles.ADMIN);
        return user;
    }
}
