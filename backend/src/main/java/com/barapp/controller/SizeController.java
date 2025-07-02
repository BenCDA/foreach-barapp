package com.barapp.controller;

import com.barapp.dto.SizeRequest;
import com.barapp.dto.SizeResponse;
import com.barapp.service.SizeService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    /**
     * Crée une nouvelle taille (S, M ou L).
     * Accessible uniquement au rôle BARMAN.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public ResponseEntity<SizeResponse> createSize(@RequestBody @Valid SizeRequest request) {
        SizeResponse created = sizeService.create(request);
        return ResponseEntity
                .created(URI.create("/api/sizes/" + created.getId()))
                .body(created);
    }

    /**
     * Liste toutes les tailles.
     * Accessible aux rôles BARMAN et CLIENT.
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_BARMAN','ROLE_CLIENT')")
    public List<SizeResponse> listSizes() {
        return sizeService.findAll();
    }

    /**
     * Supprime une taille par son ID.
     * Accessible uniquement au rôle BARMAN.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
