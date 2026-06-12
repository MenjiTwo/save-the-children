package com.infoman.backend.service;

import com.infoman.backend.dto.ApplicantRequest;
import com.infoman.backend.dto.LicenseCertificationDTO;
import com.infoman.backend.dto.VolunteerEngagementDTO;
import com.infoman.backend.model.*;
import com.infoman.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final SkillRepository skillRepository;
    private final InterestRepository interestRepository;

    public ApplicantService(ApplicantRepository applicantRepository,
                            SkillRepository skillRepository,
                            InterestRepository interestRepository) {
        this.applicantRepository = applicantRepository;
        this.skillRepository = skillRepository;
        this.interestRepository = interestRepository;
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Optional<Applicant> getApplicantById(String id) {
        return applicantRepository.findById(id);
    }

    @Transactional
    public Applicant createApplicant(ApplicantRequest request) {
        String nextId = generateNextApplicantId();
        request.setApplicantId(nextId);

        Applicant applicant = new Applicant();
        mapRequestToApplicant(request, applicant);
        return applicantRepository.save(applicant);
    }

    private String generateNextApplicantId() {
        String maxId = applicantRepository.findMaxApplicantId();
        if (maxId != null && maxId.startsWith("A")) {
            try {
                int idNum = Integer.parseInt(maxId.substring(1));
                return String.format("A%03d", idNum + 1);
            } catch (NumberFormatException ignored) {
            }
        }
        return "A001";
    }

    @Transactional
    public Applicant updateApplicant(String id, ApplicantRequest request) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));

        mapRequestToApplicant(request, applicant);
        applicant.setApplicantId(id); // ensure ID is not changed
        return applicantRepository.save(applicant);
    }

    @Transactional
    public void deleteApplicant(String id) {
        if (!applicantRepository.existsById(id)) {
            throw new RuntimeException("Applicant not found with id: " + id);
        }
        applicantRepository.deleteById(id);
    }

    public List<Applicant> searchApplicants(String lastName, String skillCode,
                                            String language, String interestCode,
                                            Boolean willingToDeploy) {
        // Start with all applicants and progressively filter
        List<Applicant> results = null;

        if (lastName != null && !lastName.isEmpty()) {
            results = applicantRepository.findByLastNameContainingIgnoreCase(lastName);
        }

        if (skillCode != null && !skillCode.isEmpty()) {
            List<Applicant> skillResults = applicantRepository.findBySkills_SkillCode(skillCode);
            results = intersect(results, skillResults);
        }

        if (language != null && !language.isEmpty()) {
            List<Applicant> langResults = applicantRepository.findByLanguages(language);
            results = intersect(results, langResults);
        }

        if (interestCode != null && !interestCode.isEmpty()) {
            List<Applicant> interestResults = applicantRepository.findByInterests_InterestCode(interestCode);
            results = intersect(results, interestResults);
        }

        if (willingToDeploy != null) {
            List<Applicant> deployResults = applicantRepository.findByWillingToDeploy(willingToDeploy);
            results = intersect(results, deployResults);
        }

        if (results == null) {
            return applicantRepository.findAll();
        }

        return results;
    }

    private List<Applicant> intersect(List<Applicant> existing, List<Applicant> newResults) {
        if (existing == null) {
            return new ArrayList<>(newResults);
        }
        Set<String> ids = newResults.stream()
                .map(Applicant::getApplicantId)
                .collect(Collectors.toSet());
        return existing.stream()
                .filter(a -> ids.contains(a.getApplicantId()))
                .collect(Collectors.toList());
    }

    private void mapRequestToApplicant(ApplicantRequest request, Applicant applicant) {
        applicant.setApplicantId(request.getApplicantId());
        applicant.setDateSubmitted(request.getDateSubmitted());
        applicant.setLastName(request.getLastName());
        applicant.setFirstName(request.getFirstName());
        applicant.setMiddleName(request.getMiddleName());
        applicant.setNickname(request.getNickname());
        applicant.setAddress(request.getAddress());
        applicant.setPhoneLandline(request.getPhoneLandline());
        applicant.setMobilePhone(request.getMobilePhone());
        applicant.setPermanentAddress(request.getPermanentAddress());
        applicant.setGender(request.getGender());
        applicant.setEmailAddress(request.getEmailAddress());
        applicant.setBirthdate(request.getBirthdate());
        applicant.setBirthplace(request.getBirthplace());
        applicant.setCitizenship(request.getCitizenship());
        applicant.setHeight(request.getHeight());
        applicant.setWeight(request.getWeight());
        applicant.setEmployerName(request.getEmployerName());
        applicant.setHighestEducLevel(request.getHighestEducLevel());
        applicant.setEducSchoolAndAddress(request.getEducSchoolAndAddress());
        applicant.setEducFrom(request.getEducFrom());
        applicant.setEducTo(request.getEducTo());
        applicant.setEducDegree(request.getEducDegree());
        applicant.setAvailabilityOption(request.getAvailabilityOption());
        applicant.setHoursPerDay(request.getHoursPerDay());
        applicant.setWillingToDeploy(request.getWillingToDeploy());
        applicant.setWillingHumanitarian(request.getWillingHumanitarian());
        applicant.setReferenceNameRelationship(request.getReferenceNameRelationship());
        applicant.setCompanyOrgInfo(request.getCompanyOrgInfo());
        applicant.setPosition(request.getPosition());
        applicant.setContactPerson(request.getContactPerson());
        applicant.setContactPhoneNo(request.getContactPhoneNo());
        applicant.setContactRelationship(request.getContactRelationship());
        applicant.setContactAddress(request.getContactAddress());

        // Resolve languages
        if (request.getLanguages() != null) {
            applicant.setLanguages(new HashSet<>(request.getLanguages()));
        } else {
            applicant.setLanguages(new HashSet<>());
        }

        // Resolve skills
        if (request.getSkillCodes() != null) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(request.getSkillCodes()));
            applicant.setSkills(skills);
        } else {
            applicant.setSkills(new HashSet<>());
        }

        // Resolve interests
        if (request.getInterestCodes() != null) {
            Set<Interest> interests = new HashSet<>(interestRepository.findAllById(request.getInterestCodes()));
            applicant.setInterests(interests);
        } else {
            applicant.setInterests(new HashSet<>());
        }

        // Handle license certifications
        if (request.getLicenseCertification() != null) {
            LicenseCertificationDTO dto = request.getLicenseCertification();
            LicenseCertification lc = new LicenseCertification();
            lc.setLicenseCertification(dto.getLicenseCertification());
            lc.setDateIssued(dto.getDateIssued());
            lc.setApplicant(applicant);
            applicant.setLicenseCertification(lc);
        } else {
            applicant.setLicenseCertification(null);
        }

        // Handle volunteer engagements
        if (request.getVolunteerEngagement() != null) {
            VolunteerEngagementDTO dto = request.getVolunteerEngagement();
            VolunteerEngagement ve = new VolunteerEngagement();
            ve.setNameOfCompany(dto.getNameOfCompany());
            ve.setPositionHeld(dto.getPositionHeld());
            ve.setLengthOfServiceFrom(dto.getLengthOfServiceFrom() != null ? java.time.LocalDate.of(dto.getLengthOfServiceFrom(), 1, 1) : null);
            ve.setLengthOfServiceTo(dto.getLengthOfServiceTo() != null ? java.time.LocalDate.of(dto.getLengthOfServiceTo(), 1, 1) : null);
            ve.setVolunteerEngagementSummary(dto.getVolunteerEngagementSummary());
            ve.setApplicant(applicant);
            applicant.setVolunteerEngagement(ve);
        } else {
            applicant.setVolunteerEngagement(null);
        }
    }
}
