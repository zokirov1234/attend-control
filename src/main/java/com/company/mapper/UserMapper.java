package com.company.mapper;

import com.company.model.dto.user.UserRegisterDTO;
import com.company.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "role")
    UserRegisterDTO userToUserDTO(UserEntity user);

    @Mapping(source = "role", target = "roles")
    UserEntity userDTOToUser(UserRegisterDTO userDTO);
}
