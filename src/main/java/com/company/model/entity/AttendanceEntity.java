package com.company.model.entity;

import com.company.model.enums.AttendAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "attendance")
@Builder
@DynamicInsert
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private AttendAction action;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity studentId;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
