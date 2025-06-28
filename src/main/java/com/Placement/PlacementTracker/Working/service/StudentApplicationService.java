package com.Placement.PlacementTracker.Working.service;

import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import com.Placement.PlacementTracker.Working.model.StudentApplication;
import com.Placement.PlacementTracker.Working.model.StudentDetails;
import com.Placement.PlacementTracker.Working.repository.CompanyNotificationRepository;
import com.Placement.PlacementTracker.Working.repository.StudentApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentApplicationService {

    private final CompanyNotificationRepository companyRepository;
    private final StudentApplicationRepository studentApplicationRepository;

    public ResponseEntity<String> applyToCompany(int company_id, StudentDetails student) {
        CompanyNotification company = companyRepository
                .findById(company_id)
                .orElseThrow(() -> new RuntimeException("Company not found!"));

        System.out.println("company: "+ company);

        boolean alreadyApplied = studentApplicationRepository.existsByStudentAndCompany(student, company);
        if(alreadyApplied)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already applied");

        company.setApplicationCount(company.getApplicationCount() + 1);
        companyRepository.save(company);
            
        StudentApplication application =
                StudentApplication
                        .builder()
                        .company(company)
                        .student(student)
                        .applied_at(new Date())
                        .build();


        studentApplicationRepository.save(application);
        return ResponseEntity.ok("Applied Successfully!");
    }

    public ResponseEntity<String> withdrawFromCompany(int companyId, StudentDetails student) {
        CompanyNotification company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));
        Optional<StudentApplication> appOpt = studentApplicationRepository.findByStudentAndCompany(student, company);

        if(appOpt.isPresent()){
            studentApplicationRepository.delete(appOpt.get());
            company.setApplicationCount(company.getApplicationCount() - 1);
            companyRepository.save(company);
            return ResponseEntity.ok("Withdrawn successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found");
    }
//    StudentApplication apply(StudentApplication application);
//    Page<StudentApplication> getAllApplications(int page, int size);
//    Page<StudentApplication> getApplicationsByStudent(String studentName, int page, int size);
//    Page<StudentApplication> getApplicationsByCompany(String companyName, int page, int size);
//    StudentApplication updateApplicationStatus(Long id, String status);
//    Page<StudentApplication> getApplicationsByStatus(String status, int page, int size);
//    Page<StudentApplication> getAllApplicationsByStatusAndCompany(String status, String company, int page, int size);
}
