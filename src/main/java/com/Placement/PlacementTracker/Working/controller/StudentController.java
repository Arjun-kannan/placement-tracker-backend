package com.Placement.PlacementTracker.Working.controller;

import com.Placement.PlacementTracker.Working.dto.CompanyNotificationResponse;
import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import com.Placement.PlacementTracker.Working.model.StudentDetails;
import com.Placement.PlacementTracker.Working.repository.StudentApplicationRepository;
import com.Placement.PlacementTracker.Working.service.CompanyNotificationService;
import com.Placement.PlacementTracker.Working.service.StudentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
@RequestMapping("/student")
public class StudentController {

    private final CompanyNotificationService notificationService;
    private final StudentApplicationService applicationService;

    @GetMapping("/company")
    public ResponseEntity<Page<CompanyNotificationResponse>> getCompanies(
            @AuthenticationPrincipal StudentDetails student,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
            ){
        return ResponseEntity.ok(notificationService.findAllCompanies(student, page, size));
    }

    @PostMapping("/apply/{company_id}")
    public ResponseEntity<?> applyToCompany(
            @AuthenticationPrincipal StudentDetails student,
            @PathVariable int company_id
    ){
        applicationService.applyToCompany(company_id, student);
        return ResponseEntity.ok("applied successfully");
    }

    @DeleteMapping("/withdraw/{company_id}")
    public ResponseEntity<?> withdrawFromCompany(
            @AuthenticationPrincipal StudentDetails student,
            @PathVariable int company_id
    ){
        applicationService.withdrawFromCompany(company_id, student);
        return ResponseEntity.ok("Application removed successfully");
    }











//    @Deprecated
//    @PostMapping("/apply")
//    public StudentApplication addApplication(@RequestBody StudentApplication application){
//        System.out.println(application);
//        return applicationService.apply(application);
//    }
//
//    @Deprecated
//    @GetMapping("/applications/me")
//    public ResponseEntity<Page<StudentApplication>> viewAllApplicationsByStudent(
//            @AuthenticationPrincipal User currentUser,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size){
//        System.out.println("Current user: " + currentUser.getName());
//        return new ResponseEntity<>(applicationService.getApplicationsByStudent(currentUser.getName(), page, size), HttpStatus.OK);
//    }

}
