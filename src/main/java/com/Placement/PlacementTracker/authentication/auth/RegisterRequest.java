package com.Placement.PlacementTracker.authentication.auth;

import com.Placement.PlacementTracker.authentication.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String rollNumber;
    private String name;
    private float cgpa;
    private boolean backlogHistory;
    private int activeBacklog;
    private String email;
    private String password;
}
