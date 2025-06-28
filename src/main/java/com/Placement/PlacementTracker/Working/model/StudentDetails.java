package com.Placement.PlacementTracker.Working.model;

import com.Placement.PlacementTracker.authentication.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentDetails extends User {

    private float cgpa;
    private boolean backlogHistory;
    private int activeBacklog;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonBackReference
    @ToString.Exclude
    private List<StudentApplication> applications = new ArrayList<>();
}
