package com.infoman.backend.controller;

import com.infoman.backend.model.Interest;
import com.infoman.backend.model.Language;
import com.infoman.backend.model.Skill;
import com.infoman.backend.service.CatalogService;
import com.infoman.backend.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    private final CatalogService catalogService;
    private final LanguageService languageService;

    public CatalogController(CatalogService catalogService,
                             LanguageService languageService) {
        this.catalogService = catalogService;
        this.languageService = languageService;
    }


    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(catalogService.getAllSkills());
    }

    @GetMapping("/interests")
    public ResponseEntity<List<Interest>> getAllInterests() {
        return ResponseEntity.ok(catalogService.getAllInterests());
    }

    /**
     * GET /api/catalog/languages
     * Returns all languages from the catalog (sorted A-Z).
     * Supports optional ?search= query param for dropdown filtering.
     */
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLanguages(
            @RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(languageService.searchLanguages(search));
        }
        return ResponseEntity.ok(languageService.getAllLanguages());
    }
}
