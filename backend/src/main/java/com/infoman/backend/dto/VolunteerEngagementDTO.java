package com.infoman.backend.dto;

import java.time.LocalDate;

public class VolunteerEngagementDTO {

    private String nameOfCompany;
    private String positionHeld;
    private Integer lengthOfServiceFrom;
    private Integer lengthOfServiceTo;
    private String volunteerEngagementSummary;

    public VolunteerEngagementDTO() {
    }

    public VolunteerEngagementDTO(String nameOfCompany, String positionHeld,
                                  Integer lengthOfServiceFrom, Integer lengthOfServiceTo,
                                  String volunteerEngagementSummary) {
        this.nameOfCompany = nameOfCompany;
        this.positionHeld = positionHeld;
        this.lengthOfServiceFrom = lengthOfServiceFrom;
        this.lengthOfServiceTo = lengthOfServiceTo;
        this.volunteerEngagementSummary = volunteerEngagementSummary;
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

    public Integer getLengthOfServiceFrom() {
        return lengthOfServiceFrom;
    }

    public void setLengthOfServiceFrom(Integer lengthOfServiceFrom) {
        this.lengthOfServiceFrom = lengthOfServiceFrom;
    }

    public Integer getLengthOfServiceTo() {
        return lengthOfServiceTo;
    }

    public void setLengthOfServiceTo(Integer lengthOfServiceTo) {
        this.lengthOfServiceTo = lengthOfServiceTo;
    }

    public String getVolunteerEngagementSummary() {
        return volunteerEngagementSummary;
    }

    public void setVolunteerEngagementSummary(String volunteerEngagementSummary) {
        this.volunteerEngagementSummary = volunteerEngagementSummary;
    }
}
