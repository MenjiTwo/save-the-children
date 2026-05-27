package com.infoman.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_Catalog")
public class Skill {

    @Id
    @Column(name = "skill_code")
    private String skillCode;

    @Column(name = "acquired_skills", nullable = false)
    private String acquiredSkills;

    public Skill() {
    }

    public Skill(String skillCode, String acquiredSkills) {
        this.skillCode = skillCode;
        this.acquiredSkills = acquiredSkills;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public void setSkillCode(String skillCode) {
        this.skillCode = skillCode;
    }

    public String getAcquiredSkills() {
        return acquiredSkills;
    }

    public void setAcquiredSkills(String acquiredSkills) {
        this.acquiredSkills = acquiredSkills;
    }
}
