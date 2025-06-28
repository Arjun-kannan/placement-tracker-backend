package com.Placement.PlacementTracker.Working.controller;

import com.Placement.PlacementTracker.Working.dto.CompanyNotificationResponse;
import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import com.Placement.PlacementTracker.Working.service.CompanyNotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final CompanyNotificationService adminService;

    @PostMapping("/company/add")
    public ResponseEntity<CompanyNotification> addCompany(@RequestBody CompanyNotification notification){
        return ResponseEntity.ok(adminService.addCompany(notification));
    }

    @GetMapping("/company")
    public ResponseEntity<Page<CompanyNotificationResponse>> findAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.ok(adminService.findAllCompanies(page, size));
    }

    @GetMapping("/company/{company_id}/export")
    public void exportCompanyApplicants(
            @PathVariable int company_id,
            HttpServletResponse response
    ) throws IOException {
        adminService.exportCompanyApplicants(company_id, response);
        return;
    }
}
