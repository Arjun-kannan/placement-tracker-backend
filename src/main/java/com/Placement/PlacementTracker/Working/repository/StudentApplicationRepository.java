package com.Placement.PlacementTracker.Working.repository;

import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import com.Placement.PlacementTracker.Working.model.StudentApplication;
import com.Placement.PlacementTracker.Working.model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentApplicationRepository extends JpaRepository<StudentApplication, Integer> {

    boolean existsByStudentAndCompany(StudentDetails student, CompanyNotification company);

    Optional<StudentApplication> findByStudentAndCompany(StudentDetails student, CompanyNotification company);

    List<StudentApplication> findByCompany(CompanyNotification company);

    List<StudentApplication> findByStudentId(Integer id);

    List<StudentApplication> findByCompanyId(int company_id);

//
//    Page<StudentApplication> findByStudentName(String studentName, Pageable pageable);
//
//    @Query(value = "SELECT * from student_application WHERE status = :status AND company_name = :company", nativeQuery = true)
//    Page<StudentApplication> findByStatusAndCompany(@Param("status") String status, @Param("company")String company, Pageable pageable);
//
//    Page<StudentApplication> findByStatus(String status, Pageable pageable);
//
//    Page<StudentApplication> findByCompanyName(String companyName, Pageable pageable);

}
