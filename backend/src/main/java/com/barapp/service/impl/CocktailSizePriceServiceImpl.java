package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.repository.SizeRepository;
import com.barapp.service.CocktailSizePriceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailSizePriceServiceImpl implements CocktailSizePriceService {

    private final CocktailSizePriceRepository repo;
    private final CocktailRepository cocktailRepo;
    private final SizeRepository sizeRepo;

    @Override
    public CocktailSizePriceResponse create(CocktailSizePriceRequest req) {
        var entity = new com.barapp.model.CocktailSizePrice();
        entity.setCocktail(cocktailRepo.findById(req.getCocktailId())
                                       .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable")));
        entity.setSize(sizeRepo.findById(req.getSizeId())
                              .orElseThrow(() -> new IllegalArgumentException("Size introuvable")));
        entity.setPrice(req.getPrice());
        var saved = repo.save(entity);
        return new CocktailSizePriceResponse(saved.getId(), saved.getCocktail().getId(),
                                            saved.getSize().getId(), saved.getPrice());
    }

    @Override
    public List<CocktailSizePriceResponse> getAll() {
        return repo.findAll().stream()
                   .map(csp -> new CocktailSizePriceResponse(csp.getId(), csp.getCocktail().getId(),
                                                           csp.getSize().getId(), csp.getPrice()))
                   .toList();
    }

    @Override
    public CocktailSizePriceResponse getById(Long id) {
        var csp = repo.findById(id)
                      .orElseThrow(() -> new IllegalArgumentException("CocktailSizePrice introuvable"));
        return new CocktailSizePriceResponse(csp.getId(), csp.getCocktail().getId(),
                                             csp.getSize().getId(), csp.getPrice());
    }

    @Override
    public CocktailSizePriceResponse update(Long id, CocktailSizePriceRequest req) {
        var csp = repo.findById(id)
                      .orElseThrow(() -> new IllegalArgumentException("CocktailSizePrice introuvable"));
        csp.setCocktail(cocktailRepo.findById(req.getCocktailId())
                                   .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable")));
        csp.setSize(sizeRepo.findById(req.getSizeId())
                            .orElseThrow(() -> new IllegalArgumentException("Size introuvable")));
        csp.setPrice(req.getPrice());
        var updated = repo.save(csp);
        return new CocktailSizePriceResponse(updated.getId(), updated.getCocktail().getId(),
                                            updated.getSize().getId(), updated.getPrice());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
