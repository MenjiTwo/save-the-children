package com.infoman.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interest_Catalog")
public class Interest {

    @Id
    @Column(name = "interest_code")
    private String interestCode;

    @Column(name = "areas_of_interest", nullable = false)
    private String areasOfInterest;

    public Interest() {
    }

    public Interest(String interestCode, String areasOfInterest) {
        this.interestCode = interestCode;
        this.areasOfInterest = areasOfInterest;
    }

    public String getInterestCode() {
        return interestCode;
    }

    public void setInterestCode(String interestCode) {
        this.interestCode = interestCode;
    }

    public String getAreasOfInterest() {
        return areasOfInterest;
    }

    public void setAreasOfInterest(String areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }
}
