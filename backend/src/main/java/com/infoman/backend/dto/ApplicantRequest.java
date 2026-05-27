package com.infoman.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ApplicantRequest {

    private String applicantId;
    private LocalDate dateSubmitted;
    private String lastName;
    private String firstName;
    private String middleName;
    private String nickname;
    private String address;
    private String phoneLandline;
    private String mobilePhone;
    private String permanentAddress;
    private String gender;
    private String emailAddress;
    private LocalDate birthdate;
    private String birthplace;
    private String citizenship;
    private BigDecimal height;
    private BigDecimal weight;
    private String employerName;
    private String highestEducLevel;
    private String educSchoolAndAddress;
    private Integer educFrom;
    private Integer educTo;
    private String educDegree;
    private String availabilityOption;
    private Integer hoursPerDay;
    private Boolean willingToDeploy;
    private Boolean willingHumanitarian;
    private String referenceNameRelationship;
    private String companyOrgInfo;
    private String position;
    private String contactPerson;
    private String contactPhoneNo;
    private String contactRelationship;
    private String contactAddress;

    private List<String> languages;
    private List<String> skillCodes;
    private List<String> interestCodes;
    private LicenseCertificationDTO licenseCertification;
    private VolunteerEngagementDTO volunteerEngagement;

    public ApplicantRequest() {
    }

    // Getters and Setters

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneLandline() {
        return phoneLandline;
    }

    public void setPhoneLandline(String phoneLandline) {
        this.phoneLandline = phoneLandline;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getHighestEducLevel() {
        return highestEducLevel;
    }

    public void setHighestEducLevel(String highestEducLevel) {
        this.highestEducLevel = highestEducLevel;
    }

    public String getEducSchoolAndAddress() {
        return educSchoolAndAddress;
    }

    public void setEducSchoolAndAddress(String educSchoolAndAddress) {
        this.educSchoolAndAddress = educSchoolAndAddress;
    }

    public Integer getEducFrom() {
        return educFrom;
    }

    public void setEducFrom(Integer educFrom) {
        this.educFrom = educFrom;
    }

    public Integer getEducTo() {
        return educTo;
    }

    public void setEducTo(Integer educTo) {
        this.educTo = educTo;
    }

    public String getEducDegree() {
        return educDegree;
    }

    public void setEducDegree(String educDegree) {
        this.educDegree = educDegree;
    }

    public String getAvailabilityOption() {
        return availabilityOption;
    }

    public void setAvailabilityOption(String availabilityOption) {
        this.availabilityOption = availabilityOption;
    }

    public Integer getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(Integer hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public Boolean getWillingToDeploy() {
        return willingToDeploy;
    }

    public void setWillingToDeploy(Boolean willingToDeploy) {
        this.willingToDeploy = willingToDeploy;
    }

    public Boolean getWillingHumanitarian() {
        return willingHumanitarian;
    }

    public void setWillingHumanitarian(Boolean willingHumanitarian) {
        this.willingHumanitarian = willingHumanitarian;
    }

    public String getReferenceNameRelationship() {
        return referenceNameRelationship;
    }

    public void setReferenceNameRelationship(String referenceNameRelationship) {
        this.referenceNameRelationship = referenceNameRelationship;
    }

    public String getCompanyOrgInfo() {
        return companyOrgInfo;
    }

    public void setCompanyOrgInfo(String companyOrgInfo) {
        this.companyOrgInfo = companyOrgInfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public String getContactRelationship() {
        return contactRelationship;
    }

    public void setContactRelationship(String contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getSkillCodes() {
        return skillCodes;
    }

    public void setSkillCodes(List<String> skillCodes) {
        this.skillCodes = skillCodes;
    }

    public List<String> getInterestCodes() {
        return interestCodes;
    }

    public void setInterestCodes(List<String> interestCodes) {
        this.interestCodes = interestCodes;
    }

    public LicenseCertificationDTO getLicenseCertification() {
        return licenseCertification;
    }

    public void setLicenseCertification(LicenseCertificationDTO licenseCertification) {
        this.licenseCertification = licenseCertification;
    }

    public VolunteerEngagementDTO getVolunteerEngagement() {
        return volunteerEngagement;
    }

    public void setVolunteerEngagement(VolunteerEngagementDTO volunteerEngagement) {
        this.volunteerEngagement = volunteerEngagement;
    }
}
