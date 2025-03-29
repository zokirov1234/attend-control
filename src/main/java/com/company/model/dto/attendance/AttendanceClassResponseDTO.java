package com.company.model.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceClassResponseDTO {

    private String studentName;
    private String className;
    private LocalDateTime dateTime;
}
