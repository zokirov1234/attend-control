package com.company.service.impl;

import com.company.config.JwtService;
import com.company.enums.Roles;
import com.company.mapper.UserMapper;
import com.company.model.dto.user.UserDTO;
import com.company.model.dto.user.UserLoginDTO;
import com.company.model.dto.user.UserRegisterDTO;
import com.company.model.entity.UserEntity;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.company.util.ResponseBaseUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> doRegister(UserRegisterDTO userRegisterDTO) {
        try {
            userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            UserEntity user = userRepository.save(userMapper.userDTOToUser(userRegisterDTO));
            return buildSuccessResponse(userMapper.userToUserDTO(user), "SUCCESS");
        } catch (Exception exception) {
            log.warn("User creation failed, Item found exception");
            return buildInternalErrorResponse(null, exception.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> doLogin(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> user
                = userRepository.findByUsername(userLoginDTO.getUsername());
        if (user.isEmpty()) {
            log.warn("There is no user with this username");
            return buildInternalErrorResponse(null, "There is no user with this username");
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        if (!user.get().getState()) {
            log.warn("User has been deleted");
            return buildForbiddenErrorResponse(null, "User has been deleted");
        }
        if (!auth.isAuthenticated()) {
            log.warn("There is no user with this username");
            return buildInternalErrorResponse(null, "Username or password is incorrect");
        }
        log.info("logged success");
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtService.generateToken(userLoginDTO.getUsername()));
        map.put("username", user.get().getUsername());
        map.put("role", user.get().getRoles().toString());
        return buildSuccessResponse(map, "SUCCESS");
    }

    @Override
    public ResponseEntity<?> getUserById(Integer id) {
        return buildSuccessResponse(userMapper.userToUserDTO(userRepository.findUserById(id)), "SUCCESS");
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateUserById(int id, UserDTO userDTO) {
        try {
            Optional<UserEntity> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty() || Boolean.FALSE.equals(userOpt.get().getState())) {
                log.warn("User not found");
                return buildInternalErrorResponse(null, "There is no user with this id");
            }
            UserEntity user = userOpt.get();
            if (Objects.isNull(userDTO.getPassword())) {
                userDTO.setPassword(user.getPassword());
            } else {
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            Roles role = (userDTO.getRoles() != null) ? Roles.valueOf(userDTO.getRoles()) : user.getRoles();
            userRepository.updateUser(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    role, id);
            return buildSuccessResponse(null, "User updated successfully");
        } catch (Exception e) {
            log.error("Error occurred while updating user {}", e.getMessage());
            return buildInternalErrorResponse(null, "Error occurred while updating user");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteUserById(int id) {
        try {
            String username = userRepository.findUserById(id).getUsername();
            if (Objects.isNull(username)) {
                log.warn("There is no user with this id");
                return buildInternalErrorResponse(null, "There is no user with this id");
            }
            String newUsername = UUID.randomUUID() + "_" + username;
            userRepository.deleteUserById(newUsername, id);
            log.info("User deleted successfully");
            return buildSuccessResponse(null, "User deleted successfully");
        } catch (Exception e) {
            log.error("Error occurred while deleting user {}", e.getMessage());
            return buildInternalErrorResponse(null, "Error occurred while deleting user");
        }
    }

}
