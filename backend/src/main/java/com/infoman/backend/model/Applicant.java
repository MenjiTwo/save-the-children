package com.infoman.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "applicant_Details")
public class Applicant {

    @Id
    @Column(name = "applicant_id")
    private String applicantId;

    @Column(name = "date_submitted")
    private LocalDate dateSubmitted;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_landline")
    private String phoneLandline;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "permanent_address", columnDefinition = "TEXT")
    private String permanentAddress;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "highest_educ_level")
    private String highestEducLevel;

    @Column(name = "educ_school_and_address", columnDefinition = "TEXT")
    private String educSchoolAndAddress;

    @Column(name = "educ_from")
    private Integer educFrom;

    @Column(name = "educ_to")
    private Integer educTo;

    @Column(name = "educ_degree")
    private String educDegree;

    @Column(name = "availability_option")
    private String availabilityOption;

    @Column(name = "hours_per_day")
    private Integer hoursPerDay;

    @Column(name = "willing_to_deploy")
    private Boolean willingToDeploy;

    @Column(name = "willing_humanitarian")
    private Boolean willingHumanitarian;

    @Column(name = "reference_name_relationship")
    private String referenceNameRelationship;

    @Column(name = "company_org_info")
    private String companyOrgInfo;

    @Column(name = "position")
    private String position;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_phone_no")
    private String contactPhoneNo;

    @Column(name = "contact_relationship")
    private String contactRelationship;

    @Column(name = "contact_address", columnDefinition = "TEXT")
    private String contactAddress;

    @ElementCollection
    @CollectionTable(name = "applicant_Languages", joinColumns = @JoinColumn(name = "applicant_id"))
    @Column(name = "languages_spoken")
    private Set<String> languages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "applicant_Skills",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_code"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "applicant_Interests",
            joinColumns = @JoinColumn(name = "applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_code"))
    private Set<Interest> interests = new HashSet<>();

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private LicenseCertification licenseCertification;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private VolunteerEngagement volunteerEngagement;

    public Applicant() {
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

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public LicenseCertification getLicenseCertification() {
        return licenseCertification;
    }

    public void setLicenseCertification(LicenseCertification licenseCertification) {
        this.licenseCertification = licenseCertification;
    }

    public VolunteerEngagement getVolunteerEngagement() {
        return volunteerEngagement;
    }

    public void setVolunteerEngagement(VolunteerEngagement volunteerEngagement) {
        this.volunteerEngagement = volunteerEngagement;
    }
}
