package com.infoman.backend.repository;

import com.infoman.backend.model.LicenseCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseCertificationRepository extends JpaRepository<LicenseCertification, String> {
}
