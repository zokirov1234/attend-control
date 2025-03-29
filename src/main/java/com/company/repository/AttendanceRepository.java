package com.company.repository;

import com.company.model.entity.AttendanceEntity;
import com.company.model.entity.ClassEntity;
import com.company.model.enums.AttendAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

    @Query("select attend from AttendanceEntity attend where attend.classId = ?1 and attend.action = ?2 order by attend.createdAt")
    List<AttendanceEntity> findAllByClassIdAndAction(ClassEntity classId, AttendAction action);

    @Query(value = "SELECT * FROM attendance a " +
            "WHERE a.class_id = :classId " +
            "AND a.action = 'ENTRY' " +
            "AND (EXTRACT(HOUR FROM a.created_at) > 8 " +
            "OR (EXTRACT(HOUR FROM a.created_at) = 8 AND EXTRACT(MINUTE FROM a.created_at) > 30))",
            nativeQuery = true)
    List<AttendanceEntity> findLateStudents(@Param("classId") int classId);


    @Query(value = "SELECT * FROM attendance a " +
            "WHERE a.action = 'ENTRY' " +
            "AND (EXTRACT(HOUR FROM a.created_at) > 8 " +
            "OR (EXTRACT(HOUR FROM a.created_at) = 8 AND EXTRACT(MINUTE FROM a.created_at) > 30))",
            nativeQuery = true)
    List<AttendanceEntity> findLateStudent();

    @Query("select a from AttendanceEntity a where a.action = ?1 order by a.createdAt")
    List<AttendanceEntity> findAllByAction(AttendAction action);

}
