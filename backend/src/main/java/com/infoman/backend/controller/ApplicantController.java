package com.infoman.backend.controller;

import com.infoman.backend.dto.ApplicantRequest;
import com.infoman.backend.model.Applicant;
import com.infoman.backend.service.ApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/applicants")
@CrossOrigin(origins = "*")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public ResponseEntity<?> getAllApplicants(@RequestHeader(value = "X-Role", required = false) String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }
        List<Applicant> applicants = applicantService.getAllApplicants();
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable String id) {
        Optional<Applicant> applicant = applicantService.getApplicantById(id);
        if (applicant.isPresent()) {
            return ResponseEntity.ok(applicant.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Applicant not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantRequest request) {
        try {
            Applicant created = applicantService.createApplicant(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplicant(@PathVariable String id,
                                             @RequestBody ApplicantRequest request) {
        try {
            Applicant updated = applicantService.updateApplicant(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicant(@PathVariable String id,
                                             @RequestHeader(value = "X-Role", required = false) String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }
        try {
            applicantService.deleteApplicant(id);
            return ResponseEntity.ok(Map.of("message", "Applicant deleted successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchApplicants(
            @RequestHeader(value = "X-Role", required = false) String role,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String skillCode,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String interestCode,
            @RequestParam(required = false) Boolean willingToDeploy) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied. Admin role required."));
        }
        List<Applicant> results = applicantService.searchApplicants(
                lastName, skillCode, language, interestCode, willingToDeploy);
        return ResponseEntity.ok(results);
    }
}
