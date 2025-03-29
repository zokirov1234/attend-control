package com.company.service.impl;

import com.company.mapper.AttendanceMapper;
import com.company.model.dto.attendance.AttendDTO;
import com.company.model.dto.attendance.AttendanceClassResponseDTO;
import com.company.model.entity.AttendanceEntity;
import com.company.model.entity.ClassEntity;
import com.company.model.entity.StudentEntity;
import com.company.model.enums.AttendAction;
import com.company.repository.AttendanceRepository;
import com.company.repository.ClassRepository;
import com.company.repository.StudentRepository;
import com.company.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.company.util.ResponseBaseUtil.buildInternalErrorResponse;
import static com.company.util.ResponseBaseUtil.buildSuccessResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public ResponseEntity<?> doAttend(String action, AttendDTO attendDTO) {
        AttendAction attendAction = extractAction(action);
        if (attendAction == null) {
            return buildInternalErrorResponse(null, "Error occurred while extracting action");
        }
        StudentEntity student = studentRepository.findFirstByFingerPrintId(attendDTO.getFingerPrintId());
        if (Objects.isNull(student)) {
            log.error("Student not found with finger print id {}", attendDTO.getFingerPrintId());
            return buildInternalErrorResponse(null, "Student not found with finger print id");
        }
        attendanceRepository.save(AttendanceEntity.builder()
                .action(attendAction)
                .studentId(student)
                .classId(student.getClassEntity())
                .build());
        return buildSuccessResponse(null, "Success");
    }

    @Override
    public ResponseEntity<?> getAttendanceByClass(int id) {
        Optional<ClassEntity> classOpt = classRepository.findById(id);
        if (classOpt.isEmpty()) {
            log.error("Class not found {}", id);
            return buildInternalErrorResponse(null, "Class not found");
        }
        ClassEntity classEntity = classOpt.get();
        List<AttendanceClassResponseDTO> responseDTOS = new ArrayList<>();
        for (AttendanceEntity attendance : attendanceRepository.findAllByClassIdAndAction(classEntity, AttendAction.ENTRY)) {
            AttendanceClassResponseDTO attendanceDTO = attendanceMapper.toAttendanceClass(attendance);
            responseDTOS.add(attendanceDTO);
        }
        return buildSuccessResponse(responseDTOS, "Success");
    }

    @Override
    public ResponseEntity<?> getLateStudentsByClass(int id) {
        Optional<ClassEntity> classOpt = classRepository.findById(id);
        if (classOpt.isEmpty()) {
            log.error("Class not found {}", id);
        }
        ClassEntity classEntity = classOpt.get();
        List<AttendanceClassResponseDTO> responseDTOS = new ArrayList<>();
        for (AttendanceEntity attendance : attendanceRepository.findLateStudents(classEntity.getId())) {
            responseDTOS.add(attendanceMapper.toAttendanceClass(attendance));
        }
        return buildSuccessResponse(responseDTOS, "Success");
    }

    @Override
    public ResponseEntity<?> getLateStudent() {
        List<AttendanceClassResponseDTO> responseDTOS = new ArrayList<>();
        for (AttendanceEntity attendance : attendanceRepository.findLateStudent()) {
            responseDTOS.add(attendanceMapper.toAttendanceClass(attendance));
        }
        return buildSuccessResponse(responseDTOS, "Success");
    }

    private AttendAction extractAction(String action) {
        try {
            return AttendAction.valueOf(action);
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while extracting action {}", action);
            return null;
        }
    }

}
