package com.infoman.backend.service;

import com.infoman.backend.model.Interest;
import com.infoman.backend.model.Skill;
import com.infoman.backend.repository.InterestRepository;
import com.infoman.backend.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final SkillRepository skillRepository;
    private final InterestRepository interestRepository;

    public CatalogService(SkillRepository skillRepository,
                          InterestRepository interestRepository) {
        this.skillRepository = skillRepository;
        this.interestRepository = interestRepository;
    }


    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }
}
