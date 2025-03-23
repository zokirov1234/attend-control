package com.company.service.impl;

import com.company.mapper.ClassMapper;
import com.company.model.dto.classes.ClassDTO;
import com.company.model.entity.ClassEntity;
import com.company.repository.ClassRepository;
import com.company.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.company.util.ResponseBaseUtil.buildInternalErrorResponse;
import static com.company.util.ResponseBaseUtil.buildSuccessResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final ClassMapper classMapper;

    @Override
    public ResponseEntity<?> addClass(ClassDTO classDTO) {
        log.info("Add Class: {}", classDTO);
        try {
            ClassEntity savedClass = classRepository.save(classMapper.toEntity(classDTO));
            log.info("Class added successfully {}", savedClass);
            return buildSuccessResponse(null, "SUCCESS");
        } catch (Exception e) {
            log.error("Error occurred while saving class {}", e.getMessage());
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateClass(int id, ClassDTO classDTO) {
        log.info("Update Class: {}", classDTO);
        try {
            ClassEntity savedClass = classRepository.findById(id).orElse(null);
            if (savedClass == null) {
                log.error("Class not found");
                return buildInternalErrorResponse(null, "Class not found");
            }
            savedClass.setName(classDTO.getName());
            classRepository.save(savedClass);
            log.info("Class updated successfully {}", savedClass);
            return buildSuccessResponse(null, "SUCCESS");
        } catch (Exception e) {
            log.error("Error occurred while updating class {}", e.getMessage());
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteClass(int id) {
        log.info("Delete Class: {}", id);
        try {
            ClassEntity savedClass = classRepository.findById(id).orElse(null);
            if (savedClass == null) {
                log.error("Class not found with id {}", id);
                return buildInternalErrorResponse(null, "Class not found");
            }
            classRepository.delete(savedClass);
            log.info("Class deleted successfully {}", savedClass);
            return buildSuccessResponse(null, "SUCCESS");
        } catch (Exception e) {
            log.error("Error occurred while deleting class {}", e.getMessage());
            return buildInternalErrorResponse(null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getClassById(int id) {
        log.info("Get Class: {}", id);
        ClassEntity classEntity = classRepository.findById(id).orElse(null);
        return buildSuccessResponse(classEntity, "SUCCESS");
    }

    @Override
    public ResponseEntity<?> getListOfClass() {
        List<ClassDTO> listOfClasses = new ArrayList<>();
        for (ClassEntity classEntity : classRepository.findAll()) {
            ClassDTO classDTO = classMapper.toDTO(classEntity);
            listOfClasses.add(classDTO);
        }
        return buildSuccessResponse(listOfClasses, "SUCCESS");
    }
}
