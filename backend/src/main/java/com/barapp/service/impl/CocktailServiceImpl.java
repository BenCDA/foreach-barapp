package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.repository.CocktailRepository;
import com.barapp.service.CocktailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository repo;

    @Override
    public CocktailResponse create(CocktailRequest req) {
        // TODO: mapper DTO -> entity, par ex :
        // Cocktail c = cocktailMapper.fromRequest(req);
        // Cocktail saved = repo.save(c);
        // return cocktailMapper.toResponse(saved);
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<CocktailResponse> getAll() {
        // TODO: return repo.findAll().stream().map(mapper::toResponse).toList();
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CocktailResponse getById(Long id) {
        // TODO: findById + mapping
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CocktailResponse update(Long id, CocktailRequest req) {
        // TODO: find, set champs, save, mapper
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
