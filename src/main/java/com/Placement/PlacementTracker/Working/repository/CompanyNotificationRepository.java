package com.Placement.PlacementTracker.Working.repository;

import com.Placement.PlacementTracker.Working.dto.CompanyNotificationResponse;
import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyNotificationRepository extends JpaRepository<CompanyNotification, Integer> {
    Page<CompanyNotification> findAll(@NonNull Pageable pageable);


//    @Query(value = """
//            SELECT cn.*
//            FROM company_notification cn
//            WHERE
//            """)
//    Page<CompanyNotification> findAllByCriteria(Integer id, float cgpa, int activeBacklog, boolean backlogHistory, Pageable pageable);


//    @Query(value = """
//            SELECT cn.id, cn.company_name, cn.job_description, cn.required_cgpa,
//                              cn.allow_backlog_history, cn.allow_active_backlog, cn.slab, cn.form_link,
//                              cn.timestamp, cn.validity,
//            CASE
//                WHEN :cgpa >= cn.required_cgpa
//                AND :activeBacklog <= cn.allow_active_backlog
//                AND (cn.allow_backlog_history = TRUE OR :backlogHistory = FALSE)
//                THEN true
//                ELSE false
//            END AS isEligible,
//
//            CASE
//                WHEN sa.id is NULL
//                THEN false
//                ELSE true
//            END AS hasApplied,
//                (SELECT count(*) FROM student_application sa2 WHERE sa2.company_id = cn.id) AS applicationCount,
//            from company_notification cn
//            LEFT JOIN student_application sa
//                ON sa.company_id = cn.id AND sa.student_id = :studentId
//            """,
//            nativeQuery = true)
//    Page<CompanyNotificationResponse> findAllByCriteria(
//            @Param("studentId") int studentId,
//            @Param("cgpa") float cgpa,
//            @Param("activeBacklog") int activeBacklog,
//            @Param("backlogHistory") boolean backlogHistory,
//            Pageable pageable);

}
