//package com.Placement.PlacementTracker.Working.controller;
//
//import com.Placement.PlacementTracker.Working.model.StudentApplication;
//import com.Placement.PlacementTracker.Working.service.StudentApplicationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@PreAuthorize("hasRole('ADMIN')")
//@RequestMapping("/admin/applications")
//public class AdminApplicationsController {
//
//    @Autowired
//    private StudentApplicationService applicationService;
//
//    @GetMapping("")
//    public ResponseEntity<Page<StudentApplication>> filterApplications(
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) String company,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size
//    ) {
//        if (status != null && company != null)
//            return new ResponseEntity<>(applicationService.getAllApplicationsByStatusAndCompany(status, company, page, size), HttpStatus.OK);
//        else if (status != null)
//            return new ResponseEntity<>(applicationService.getApplicationsByStatus(status, page, size), HttpStatus.OK);
//        else if (company != null)
//            return new ResponseEntity<>(applicationService.getApplicationsByCompany(company, page, size), HttpStatus.OK);
//        else
//            return new ResponseEntity<>(applicationService.getAllApplications(page, size), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<StudentApplication> updateStatus(@PathVariable Long id,
//                                                           @RequestBody Map<String, String> body) {
//        return new ResponseEntity<>(applicationService.updateApplicationStatus(id, body.get("status")), HttpStatus.OK);
//    }
//
//    @GetMapping("/{companyName}")
//    public ResponseEntity<Page<StudentApplication>> viewApplicationsByCompany(@PathVariable String companyName,
//                                                                              @RequestParam(defaultValue = "0") int page,
//                                                                              @RequestParam(defaultValue = "5") int size) {
//        return new ResponseEntity<>(applicationService.getApplicationsByCompany(companyName, page, size), HttpStatus.OK);
//    }
//
//    @GetMapping("/pending")
//    public ResponseEntity<Page<StudentApplication>> viewPendingApplication(@RequestParam(defaultValue = "0") int page,
//                                                                           @RequestParam(defaultValue = "5") int size) {
//        return new ResponseEntity<>(applicationService.getApplicationsByStatus("pending", page, size), HttpStatus.OK);
//    }
//
//}
