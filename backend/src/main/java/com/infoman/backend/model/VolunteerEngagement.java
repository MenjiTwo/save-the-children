package com.infoman.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "previous_volunteer_Engagement")
public class VolunteerEngagement {

    @Id
    @Column(name = "applicant_id")
    private String applicantId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @Column(name = "name_of_company")
    private String nameOfCompany;

    @Column(name = "position_held")
    private String positionHeld;

    @Column(name = "length_of_service_from")
    private LocalDate lengthOfServiceFrom;

    @Column(name = "length_of_service_to")
    private LocalDate lengthOfServiceTo;

    @Column(name = "volunteer_engagement_summary", columnDefinition = "TEXT")
    private String volunteerEngagementSummary;

    public VolunteerEngagement() {
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getPositionHeld() {
        return positionHeld;
    }

    public void setPositionHeld(String positionHeld) {
        this.positionHeld = positionHeld;
    }

    public LocalDate getLengthOfServiceFrom() {
        return lengthOfServiceFrom;
    }

    public void setLengthOfServiceFrom(LocalDate lengthOfServiceFrom) {
        this.lengthOfServiceFrom = lengthOfServiceFrom;
    }

    public LocalDate getLengthOfServiceTo() {
        return lengthOfServiceTo;
    }

    public void setLengthOfServiceTo(LocalDate lengthOfServiceTo) {
        this.lengthOfServiceTo = lengthOfServiceTo;
    }

    public String getVolunteerEngagementSummary() {
        return volunteerEngagementSummary;
    }

    public void setVolunteerEngagementSummary(String volunteerEngagementSummary) {
        this.volunteerEngagementSummary = volunteerEngagementSummary;
    }
}
