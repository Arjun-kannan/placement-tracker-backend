package com.Placement.PlacementTracker.Working.controller;

import com.Placement.PlacementTracker.authentication.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
@RequestMapping("/general")
public class GeneralController {

    @GetMapping("/user")
    public ResponseEntity<String> getUsername(
            @AuthenticationPrincipal User currentUser
    ){
        // System.out.println("Name: " + currentUser);
        return ResponseEntity.ok(currentUser.getName());
    }
}
