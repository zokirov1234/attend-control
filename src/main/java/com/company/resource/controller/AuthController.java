package com.company.resource.controller;

import com.company.model.dto.user.UserLoginDTO;
import com.company.model.dto.user.UserRegisterDTO;
import com.company.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("Received create user request");
        return userService.doRegister(userRegisterDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("Received login user request");
        return userService.doLogin(userLoginDTO);
    }
}
