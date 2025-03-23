package com.company.resource.controller;

import com.company.model.dto.user.UserDTO;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
            @PathVariable("id") int id
    ) {
        log.info("Rest to get user by id: {}", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDTO userDTO
            ) {
        log.info("Rest request to update user by id: {}, {}", id, userDTO);
        return userService.updateUserById(id, userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") int id
    ) {
        log.info("Rest request to delete user by id: {}", id);
        return userService.deleteUserById(id);
    }
}
