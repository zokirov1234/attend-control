package com.company.service;

import com.company.model.dto.attendance.AttendDTO;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {

    ResponseEntity<?> doAttend(String action, AttendDTO attendDTO);

    ResponseEntity<?> getAttendanceByClass(int id);

    ResponseEntity<?> getLateStudentsByClass(int id);

    ResponseEntity<?> getLateStudent();

    ResponseEntity<?> getAllAttendance();
}
