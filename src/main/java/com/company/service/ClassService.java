package com.company.service;

import com.company.model.dto.classes.ClassDTO;
import org.springframework.http.ResponseEntity;

public interface ClassService {

    ResponseEntity<?> addClass(ClassDTO classDTO);

    ResponseEntity<?> updateClass(int id, ClassDTO classDTO);

    ResponseEntity<?> deleteClass(int id);

    ResponseEntity<?> getClassById(int id);

    ResponseEntity<?> getListOfClass();
}
