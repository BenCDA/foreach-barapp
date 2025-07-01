package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.service.CocktailSizePriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailSizePriceServiceImpl implements CocktailSizePriceService {

    private final CocktailSizePriceRepository repo;

    @Override
    public CocktailSizePriceResponse create(CocktailSizePriceRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<CocktailSizePriceResponse> getAll() {
        throw new UnsupportedOperationException(" Not implemented");
    }

    @Override
    public CocktailSizePriceResponse getById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CocktailSizePriceResponse update(Long id, CocktailSizePriceRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
