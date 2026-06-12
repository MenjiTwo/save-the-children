package com.infoman.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRenamer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            jdbcTemplate.execute("RENAME TABLE applicant_Details TO applicant_details");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE applicant_licenseCertification TO applicant_license_certification");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE previous_volunteer_Engagement TO previous_volunteer_engagement");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE applicant_Languages TO applicant_languages");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE skill_Catalog TO skill_catalog");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE interest_Catalog TO interest_catalog");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE applicant_Skills TO applicant_skills");
        } catch (Exception e) {}
        try {
            jdbcTemplate.execute("RENAME TABLE applicant_Interests TO applicant_interests");
        } catch (Exception e) {}
    }
}
