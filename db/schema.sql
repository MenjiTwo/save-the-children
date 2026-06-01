DROP DATABASE IF EXISTS savethechildren_volunteer_db;
CREATE DATABASE savethechildren_volunteer_db;
USE savethechildren_volunteer_db;

CREATE TABLE applicant_Details (
    applicant_id VARCHAR(10) PRIMARY KEY,
    date_submitted DATE DEFAULT (CURRENT_DATE),
    last_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(30),
    nickname VARCHAR(30),
    address VARCHAR(150) NOT NULL,
    phone_landline VARCHAR(20),
    mobile_phone VARCHAR(20) NOT NULL,
    permanent_address VARCHAR(150) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email_address VARCHAR(100) NOT NULL,
    birthdate DATE NOT NULL,
    birthplace VARCHAR(100) NOT NULL,
    citizenship VARCHAR(30) NOT NULL,
    height DECIMAL(5,2) NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    employer_name VARCHAR(50),
    highest_educ_level VARCHAR(30) NOT NULL,
    educ_school_and_address VARCHAR(150) NOT NULL,
    educ_from INT(4) NOT NULL,
    educ_to INT(4),
    educ_degree VARCHAR(100),
    availability_option VARCHAR(50) NOT NULL,
    hours_per_day INT(2) NOT NULL,
    willing_to_deploy BOOLEAN NOT NULL,
    willing_humanitarian BOOLEAN NOT NULL,
    reference_name_relationship VARCHAR(30) NOT NULL,
    company_org_info VARCHAR(25),
    position VARCHAR(25),
    contact_person VARCHAR(30) NOT NULL,
    contact_phone_no VARCHAR(20) NOT NULL,
    contact_relationship VARCHAR(30) NOT NULL,
    contact_address VARCHAR(150) NOT NULL
);

CREATE TABLE applicant_licenseCertification (
    applicant_id VARCHAR(10) PRIMARY KEY,
    licenses_certifications VARCHAR(100),
    date_issued DATE,
    FOREIGN KEY (applicant_id) REFERENCES applicant_Details(applicant_id) ON DELETE CASCADE
);

CREATE TABLE previous_volunteer_Engagement (
    applicant_id VARCHAR(10) PRIMARY KEY,
    name_of_company VARCHAR(50),
    position_held VARCHAR(20),
    length_of_service_from DATE,
    length_of_service_to DATE,
    volunteer_engagement_summary VARCHAR(500),
    FOREIGN KEY (applicant_id) REFERENCES applicant_Details(applicant_id) ON DELETE CASCADE
);

CREATE TABLE applicant_Languages (
    applicant_id VARCHAR(10) NOT NULL,
    languages_spoken VARCHAR(15) NOT NULL,
    PRIMARY KEY (applicant_id, languages_spoken),
    FOREIGN KEY (applicant_id) REFERENCES applicant_Details(applicant_id) ON DELETE CASCADE
);

CREATE TABLE skill_Catalog (
    skill_code VARCHAR(3) PRIMARY KEY,
    acquired_skills VARCHAR(150) NOT NULL
);

CREATE TABLE interest_Catalog (
    interest_code VARCHAR(3) PRIMARY KEY,
    areas_of_interest VARCHAR(150) NOT NULL
);

CREATE TABLE applicant_Skills (
    applicant_id VARCHAR(10) NOT NULL,
    skill_code VARCHAR(3) NOT NULL,
    PRIMARY KEY (applicant_id, skill_code),
    FOREIGN KEY (applicant_id) REFERENCES applicant_Details(applicant_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_code) REFERENCES skill_Catalog(skill_code) ON DELETE CASCADE
);

CREATE TABLE applicant_Interests (
    applicant_id VARCHAR(10) NOT NULL,
    interest_code VARCHAR(3) NOT NULL,
    PRIMARY KEY (applicant_id, interest_code),
    FOREIGN KEY (applicant_id) REFERENCES applicant_Details(applicant_id) ON DELETE CASCADE,
    FOREIGN KEY (interest_code) REFERENCES interest_Catalog(interest_code) ON DELETE CASCADE
);
