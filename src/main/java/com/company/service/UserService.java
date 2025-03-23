package com.company.service;

import com.company.model.dto.user.UserDTO;
import com.company.model.dto.user.UserLoginDTO;
import com.company.model.dto.user.UserRegisterDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> doRegister(UserRegisterDTO userRegisterDTO);

    ResponseEntity<?> doLogin(UserLoginDTO userLoginDTO);

    ResponseEntity<?> getUserById(Integer id);

    ResponseEntity<?> updateUserById(int id, UserDTO userDTO);

    ResponseEntity<?> deleteUserById(int id);
}