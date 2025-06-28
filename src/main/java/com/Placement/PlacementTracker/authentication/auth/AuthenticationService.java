package com.Placement.PlacementTracker.authentication.auth;

import com.Placement.PlacementTracker.Working.model.StudentDetails;
import com.Placement.PlacementTracker.authentication.config.ApplicationConfig;
import com.Placement.PlacementTracker.authentication.service.JwtService;
import com.Placement.PlacementTracker.authentication.model.Role;
import com.Placement.PlacementTracker.authentication.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ApplicationConfig applicationConfig;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = StudentDetails.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(applicationConfig.passwordEncoder().encode(request.getPassword()))
                .rollNumber(request.getRollNumber())
                .cgpa(request.getCgpa())
                .activeBacklog(request.getActiveBacklog())
                .backlogHistory(request.isBacklogHistory())
                .role(Role.STUDENT)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.STUDENT.toString())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));;
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }

    public Boolean checkRollNumberExists(String rollNumber) {
        return userRepository.existsByRollNumber(rollNumber);
    }

    public Boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
