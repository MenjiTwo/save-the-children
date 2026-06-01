package com.infoman.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "language_Catalog")
public class Language {

    @Id
    @Column(name = "language_code", length = 5)
    private String languageCode;

    @Column(name = "language_name", nullable = false, length = 15)
    private String languageName;

    public Language() {
    }

    public Language(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
