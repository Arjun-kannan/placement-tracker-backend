package com.Placement.PlacementTracker.Working.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyNotificationResponse {

        private int id;
        private String companyName;
        private String jobDescription;
        private float requiredCgpa;
        private boolean allowBacklogHistory;
        private int allowActiveBacklog;
        private String slab;
        private String formLink;
        private Date timestamp;
        private int validity;
        @JsonProperty("eligible")
        private boolean isEligible;
        @JsonProperty("valid")
        private boolean isValid;
        private boolean hasApplied;
        private long applicationCount;
}
