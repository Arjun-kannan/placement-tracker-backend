package com.Placement.PlacementTracker.Working.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyNotification company;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDetails student;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date applied_at;

}
