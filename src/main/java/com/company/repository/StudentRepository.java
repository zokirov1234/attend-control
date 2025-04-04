package com.company.repository;

import com.company.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    StudentEntity findFirstByFingerPrintId(String fingerprint);
}
