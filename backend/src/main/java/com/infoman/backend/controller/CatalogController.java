package com.infoman.backend.controller;

import com.infoman.backend.model.Interest;
import com.infoman.backend.model.Skill;
import com.infoman.backend.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }


    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(catalogService.getAllSkills());
    }

    @GetMapping("/interests")
    public ResponseEntity<List<Interest>> getAllInterests() {
        return ResponseEntity.ok(catalogService.getAllInterests());
    }
}
