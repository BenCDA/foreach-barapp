package com.barapp.controller;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.service.CocktailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocktails")
@RequiredArgsConstructor
public class CocktailController {

    private final CocktailService cocktailService;

    /**
     * Liste tous les cocktails, ou seulement ceux d'une catégorie si `categoryId` est fourni.
     */
    @GetMapping
    public List<CocktailResponse> list(
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        if (categoryId != null) {
            return cocktailService.getByCategory(categoryId);
        }
        return cocktailService.getAll();
    }

    @GetMapping("/{id}")
    public CocktailResponse getById(@PathVariable Long id) {
        return cocktailService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CocktailResponse create(@Valid @RequestBody CocktailRequest req) {
        return cocktailService.create(req);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public CocktailResponse update(@PathVariable Long id,
                                   @Valid @RequestBody CocktailRequest req) {
        return cocktailService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cocktailService.delete(id);
    }
}
