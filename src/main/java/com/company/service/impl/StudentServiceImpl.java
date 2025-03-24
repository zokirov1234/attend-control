package com.company.service.impl;

import com.company.mapper.StudentMapper;
import com.company.model.dto.StudentDTO;
import com.company.model.entity.ClassEntity;
import com.company.model.entity.StudentEntity;
import com.company.repository.ClassRepository;
import com.company.repository.StudentRepository;
import com.company.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.util.ResponseBaseUtil.buildInternalErrorResponse;
import static com.company.util.ResponseBaseUtil.buildSuccessResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ClassRepository classRepository;

    @Override
    public ResponseEntity<?> registerStudent(StudentDTO studentDTO) {
        try {
            Optional<ClassEntity> classOpt = classRepository.findById(studentDTO.getClassId());
            if (classOpt.isEmpty()) {
                log.error("Class not found");
                return buildInternalErrorResponse(null, "Class not found");
            }
            ClassEntity classEntity = classOpt.get();
            StudentEntity student = studentMapper.toEntity(studentDTO);
            student.setClassEntity(classEntity);
            StudentEntity savedStudent = studentRepository.save(student);
            StudentDTO studentRes = studentMapper.toDTO(savedStudent);
            log.info("Student registered successfully {}", studentRes);
            return buildSuccessResponse(studentRes, "SUCCESS");
        } catch (Exception e) {
            log.error("Error occurred while registering student: {}, DTO: {}", e.getMessage(), studentDTO);
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateStudent(int studentId, StudentDTO studentDTO) {
        try {
            Optional<StudentEntity> studentOpt = studentRepository.findById(studentId);
            if (studentOpt.isEmpty()) {
                log.error("Student not found");
                return buildInternalErrorResponse(null, "Student not found");
            }
            StudentEntity student = studentOpt.get();

            if (studentDTO.getClassId() > 0) {
                ClassEntity classEntity = classRepository.findById(studentDTO.getClassId())
                        .orElseThrow(() -> new RuntimeException("Class not found"));
                student.setClassEntity(classEntity);
            }
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            student.setMiddleName(studentDTO.getMiddleName());
            log.info("Student updated successfully: {}", student);
            return buildSuccessResponse(studentMapper.toDTO(student), "Student updated successfully");
        } catch (Exception e) {
            log.error("Error updating student: {}, DTO: {}", e.getMessage(), studentDTO);
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> deleteStudent(int id) {
        try {
            Optional<StudentEntity> studentOpt = studentRepository.findById(id);
            if (studentOpt.isEmpty()) {
                log.error("Student does not exist");
                return buildInternalErrorResponse(null, "Student not found");
            }
            StudentEntity student = studentOpt.get();
            studentRepository.delete(student);
            log.info("Student deleted successfully: {}", student);
            return buildSuccessResponse(student, "Student deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting student: {}, Id: {}", e.getMessage(), id);
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getStudentById(int id) {
        Optional<StudentEntity> studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return buildSuccessResponse(null, "Student not found");
        }
        StudentEntity student = studentOpt.get();
        return buildSuccessResponse(studentMapper.toDTO(student), "SUCCESS");
    }

    @Override
    public ResponseEntity<?> getAllStudents() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (StudentEntity student : studentRepository.findAll()) {
            StudentDTO studentDTO = studentMapper.toDTO(student);
            studentDTOS.add(studentDTO);
        }
        return buildSuccessResponse(studentDTOS, "SUCCESS");
    }
}
