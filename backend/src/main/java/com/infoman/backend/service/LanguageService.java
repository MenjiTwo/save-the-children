package com.infoman.backend.service;

import com.ibm.icu.util.ULocale;
import com.infoman.backend.model.Language;
import com.infoman.backend.repository.LanguageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that manages the language catalog.
 * On startup, it uses ICU4J (Unicode CLDR) to discover all known languages
 * and seeds them into the language_Catalog table if the table is empty.
 * This gives us 180+ real-world languages automatically.
 */
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    /**
     * On application startup, populate the language_Catalog table
     * with all known languages from the Unicode CLDR via ICU4J.
     * Only seeds if the table is currently empty (first run).
     */
    @PostConstruct
    public void seedLanguagesFromICU4J() {
        if (languageRepository.count() > 0) {
            return; // Already seeded, skip
        }

        // Collect unique language names to avoid duplicates
        // (some locale codes map to the same display name)
        Map<String, String> uniqueLanguages = new LinkedHashMap<>();

        for (ULocale locale : ULocale.getAvailableLocales()) {
            String code = locale.getLanguage();
            if (code == null || code.isEmpty() || code.equals("und")) {
                continue; // Skip undefined/empty
            }

            // Get the English display name for this language
            String displayName = locale.getDisplayLanguage(ULocale.ENGLISH);
            if (displayName == null || displayName.isEmpty() || displayName.equals(code)) {
                continue; // Skip if no proper display name
            }

            // Only keep the base language (avoid duplicates from regional variants)
            if (!uniqueLanguages.containsKey(code)) {
                uniqueLanguages.put(code, displayName);
            }
        }

        // Also add common Philippine languages that might not be in all ICU builds
        Map<String, String> philippineLanguages = new LinkedHashMap<>();
        philippineLanguages.put("tl", "Tagalog");
        philippineLanguages.put("ceb", "Cebuano");
        philippineLanguages.put("ilo", "Ilocano");
        philippineLanguages.put("hil", "Hiligaynon");
        philippineLanguages.put("war", "Waray");
        philippineLanguages.put("pam", "Kapampangan");
        philippineLanguages.put("bik", "Bikol");
        philippineLanguages.put("pag", "Pangasinan");

        for (Map.Entry<String, String> entry : philippineLanguages.entrySet()) {
            uniqueLanguages.putIfAbsent(entry.getKey(), entry.getValue());
        }

        // Convert to entities and save
        List<Language> languages = uniqueLanguages.entrySet().stream()
                .map(entry -> new Language(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(Language::getLanguageName))
                .collect(Collectors.toList());

        languageRepository.saveAll(languages);
    }

    /**
     * Get all languages, sorted alphabetically by name.
     */
    public List<Language> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        languages.sort(Comparator.comparing(Language::getLanguageName));
        return languages;
    }

    /**
     * Search languages by name (case-insensitive partial match).
     * This powers the search bar inside the frontend dropdown.
     */
    public List<Language> searchLanguages(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllLanguages();
        }
        List<Language> results = languageRepository.findByLanguageNameContainingIgnoreCase(query.trim());
        results.sort(Comparator.comparing(Language::getLanguageName));
        return results;
    }
}
