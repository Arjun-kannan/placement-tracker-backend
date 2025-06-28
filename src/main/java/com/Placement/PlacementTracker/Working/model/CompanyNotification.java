package com.Placement.PlacementTracker.Working.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_notification")
public class CompanyNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;
    private String jobDescription;
    private float requiredCgpa;
    private boolean allowBacklogHistory;
    private int allowActiveBacklog;

    @Enumerated(EnumType.STRING)
    private Slab slab;

    private String formLink;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp;

    private int validity;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<StudentApplication> applications = new ArrayList<>();

    private int applicationCount = 0;
}
