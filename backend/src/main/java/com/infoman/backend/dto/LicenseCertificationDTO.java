package com.infoman.backend.dto;

import java.time.LocalDate;

public class LicenseCertificationDTO {

    private String licenseCertification;
    private LocalDate dateIssued;

    public LicenseCertificationDTO() {
    }

    public LicenseCertificationDTO(String licenseCertification, LocalDate dateIssued) {
        this.licenseCertification = licenseCertification;
        this.dateIssued = dateIssued;
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
