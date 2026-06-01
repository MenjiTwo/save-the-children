package com.infoman.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applicant_licenseCertification")
public class LicenseCertification {

    @Id
    @Column(name = "applicant_id")
    private String applicantId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @Column(name = "licenses_certifications")
    private String licenseCertification;

    @Column(name = "date_issued")
    private LocalDate dateIssued;

    public LicenseCertification() {
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

    public String getLicenseCertification() {
        return licenseCertification;
    }

    public void setLicenseCertification(String licenseCertification) {
        this.licenseCertification = licenseCertification;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }
}
