package com.infoman.backend.repository;

import com.infoman.backend.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, String> {

    List<Applicant> findByLastNameContainingIgnoreCase(String lastName);

    List<Applicant> findBySkills_SkillCode(String skillCode);

    List<Applicant> findByLanguages(String language);

    List<Applicant> findByInterests_InterestCode(String interestCode);

    List<Applicant> findByWillingToDeploy(Boolean willingToDeploy);

    @Query("SELECT MAX(a.applicantId) FROM Applicant a")
    String findMaxApplicantId();
}
