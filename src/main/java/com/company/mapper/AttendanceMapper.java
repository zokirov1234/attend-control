package com.company.mapper;

import com.company.model.dto.attendance.AttendanceClassResponseDTO;
import com.company.model.entity.AttendanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "studentId.firstName", target = "studentName")
    @Mapping(source = "classId.name", target = "className")
    @Mapping(source = "createdAt", target = "dateTime")
    AttendanceClassResponseDTO toAttendanceClass(AttendanceEntity attendanceEntity);
}
