package com.company.mapper;

import com.company.model.dto.classes.ClassDTO;
import com.company.model.entity.ClassEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    ClassEntity toEntity(ClassDTO classDTO);

    ClassDTO toDTO(ClassEntity classEntity);
}
