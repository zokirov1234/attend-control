package com.company.mapper;

import com.company.model.dto.StudentDTO;
import com.company.model.dto.classes.ClassDTO;
import com.company.model.entity.ClassEntity;
import com.company.model.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "classId", target = "classEntity.id")
    StudentEntity toEntity(StudentDTO studentDTO);

    @Mapping(source = "classEntity.id", target = "classId")
    StudentDTO toDTO(StudentEntity studentEntity);
}
