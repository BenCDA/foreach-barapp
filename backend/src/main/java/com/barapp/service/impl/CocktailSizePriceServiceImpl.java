package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.CocktailSizePrice;
import com.barapp.model.Size;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.repository.SizeRepository;
import com.barapp.service.CocktailSizePriceService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailSizePriceServiceImpl implements CocktailSizePriceService {

    private final CocktailSizePriceRepository repository;
    private final CocktailRepository cocktailRepository;
    private final SizeRepository sizeRepository;

    @Override
    public CocktailSizePriceResponse create(CocktailSizePriceRequest request) {
        Cocktail cocktail = cocktailRepository.findById(request.getCocktailId())
                .orElseThrow(() -> new EntityNotFoundException("Cocktail not found"));
        Size size = sizeRepository.findById(request.getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        CocktailSizePrice csp = new CocktailSizePrice();
        csp.setCocktail(cocktail);
        csp.setSize(size);
        csp.setPrice(request.getPrice());

        CocktailSizePrice saved = repository.save(csp);
        return new CocktailSizePriceResponse(saved.getId(), cocktail.getId(), size.getId(), saved.getPrice());
    }

    @Override
    public List<CocktailSizePriceResponse> getAll() {
        return repository.findAll().stream()
                .map(csp -> new CocktailSizePriceResponse(
                        csp.getId(),
                        csp.getCocktail().getId(),
                        csp.getSize().getId(),
                        csp.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
