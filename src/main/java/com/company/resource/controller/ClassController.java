package com.company.resource.controller;

import com.company.model.dto.classes.ClassDTO;
import com.company.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/class")
@Slf4j
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addClass(
            @RequestBody ClassDTO classDTO
    ) {
        log.info("Rest request to add class {}", classDTO);
        return classService.addClass(classDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(
            @PathVariable("id") int id
    ) {
        log.info("Rest request to get class by id {}", id);
        return classService.getClassById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<?> listCategory() {
        log.info("Get list of class");
        return classService.getListOfClass();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClass(
            @PathVariable("id") int id,
            @RequestBody ClassDTO classDTO
    ) {
        log.info("Rest request to update class with id {}", id);
        return classService.updateClass(id, classDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClass(
            @PathVariable("id") int id
    ) {
        log.info("Delete class with id {}", id);
        return classService.deleteClass(id);
    }
}
