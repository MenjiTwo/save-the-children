package com.infoman.backend.repository;

import com.infoman.backend.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {

    List<Language> findByLanguageNameContainingIgnoreCase(String name);
}
