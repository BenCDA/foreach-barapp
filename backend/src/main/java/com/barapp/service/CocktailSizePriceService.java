package com.barapp.service;

import java.util.List;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;

public interface CocktailSizePriceService {
    CocktailSizePriceResponse create(CocktailSizePriceRequest request);
    List<CocktailSizePriceResponse> getAll();
    void delete(Long id);
}
