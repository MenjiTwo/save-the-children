package com.infoman.backend.repository;

import com.infoman.backend.model.VolunteerEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerEngagementRepository extends JpaRepository<VolunteerEngagement, String> {
}
