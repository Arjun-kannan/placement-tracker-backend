//package com.Placement.PlacementTracker.Working.service;
//
//import com.Placement.PlacementTracker.Working.model.StudentApplication;
//import com.Placement.PlacementTracker.Working.repository.StudentApplicationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class StudentApplicationServiceImpl implements StudentApplicationService{
//
//    @Autowired
//    private StudentApplicationRepository applicationRepository;
//
//    @Override
//    public StudentApplication apply(StudentApplication application) {
//        application.setStatus("pending");
//        if(application.getRole() == null)
//            application.setRole("Software development");
//        return applicationRepository.save(application);
//    }
//
//    @Override
//    public Page<StudentApplication> getAllApplications(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return applicationRepository.findAll(pageable);
//    }
//
//    @Override
//    public Page<StudentApplication> getApplicationsByStudent(String studentName, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return applicationRepository.findByStudentName(studentName, pageable);
//    }
//
//    @Override
//    public Page<StudentApplication> getApplicationsByCompany(String companyName, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return applicationRepository.findByCompanyName(companyName, pageable);
//    }
//
//    @Override
//    public StudentApplication updateApplicationStatus(Long id, String status) {
//        StudentApplication app = applicationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Application not found"));
//        app.setStatus(status);
//        return applicationRepository.save(app);
//    }
//
//    @Override
//    public Page<StudentApplication> getApplicationsByStatus(String status, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return applicationRepository.findByStatus(status, pageable);
//    }
//
//    @Override
//    public Page<StudentApplication> getAllApplicationsByStatusAndCompany(String status, String company, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return applicationRepository.findByStatusAndCompany(status, company, pageable);
//    }
//}
