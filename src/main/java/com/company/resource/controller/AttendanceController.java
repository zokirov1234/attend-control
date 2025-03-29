package com.company.resource.controller;

import com.company.model.dto.attendance.AttendDTO;
import com.company.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attend")
@Slf4j
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/{action}")
    public ResponseEntity<?> attend(
            @PathVariable("action") String action,
            @RequestBody AttendDTO attendDTO
            ) {
        log.info("Received request for action {}", action);
        return attendanceService.doAttend(action, attendDTO);
    }

    @GetMapping("/student/late")
    public ResponseEntity<?> lateAttend(){
        log.info("Received request for late student");
        return attendanceService.getLateStudent();
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<?> getClassStudentAttendance(
            @PathVariable("id") int id
    ) {
        log.info("Received request getting student attendance by class id {}", id);
        return attendanceService.getAttendanceByClass(id);
    }

    @GetMapping("/class/late/{id}")
    public ResponseEntity<?> getLateStudentClassAttendance(
            @PathVariable("id") int id
    ) {
        log.info("Received request getting late student attendance by class id {} ", id);
        return attendanceService.getLateStudentsByClass(id);
    }


}
