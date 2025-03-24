package com.company.service;

import com.company.model.dto.StudentDTO;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<?> registerStudent(StudentDTO studentDTO);

    ResponseEntity<?> updateStudent(int id, StudentDTO studentDTO);

    ResponseEntity<?> deleteStudent(int id);

    ResponseEntity<?> getStudentById(int id);

    ResponseEntity<?> getAllStudents();
}
