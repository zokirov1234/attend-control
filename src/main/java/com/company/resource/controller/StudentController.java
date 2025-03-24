package com.company.resource.controller;

import com.company.model.dto.StudentDTO;
import com.company.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody StudentDTO studentDTO
    ) {
        log.info("Registering student: {}", studentDTO);
        return studentService.registerStudent(studentDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable("id") int id,
            @RequestBody StudentDTO studentDTO
    ) {
        log.info("Updating student: {}", studentDTO);
        return studentService.updateStudent(id, studentDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable("id") int id
    ) {
        log.info("Deleting student: {}", id);
        return studentService.deleteStudent(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
        log.info("Getting student by id: {}", id);
        return studentService.getStudentById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        log.info("Getting all students");
        return studentService.getAllStudents();
    }

}
