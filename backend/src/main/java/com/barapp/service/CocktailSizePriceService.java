package com.barapp.service;

import java.util.List;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;

public interface CocktailSizePriceService {
    CocktailSizePriceResponse create(CocktailSizePriceRequest request);
    List<CocktailSizePriceResponse> getAll();
    CocktailSizePriceResponse getById(Long id);
    CocktailSizePriceResponse update(Long id, CocktailSizePriceRequest request);
    void delete(Long id);

    // --- AJOUT NOUVELLE METHODE ---
    List<CocktailSizePriceResponse> getByCocktailId(Long cocktailId);
}
