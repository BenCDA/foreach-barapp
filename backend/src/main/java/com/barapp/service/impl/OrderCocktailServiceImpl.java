package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import com.barapp.model.Size;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.SizeRepository;
import com.barapp.service.OrderCocktailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository orderCocktailRepository;
    private final OrderRepository orderRepository;
    private final CocktailRepository cocktailRepository;
    private final SizeRepository sizeRepository;

    @Override
    public OrderCocktailResponse create(OrderCocktailRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Cocktail cocktail = cocktailRepository.findById(request.getCocktailId())
                .orElseThrow(() -> new EntityNotFoundException("Cocktail not found"));

        Size size = sizeRepository.findById(request.getSizeId())
                .orElseThrow(() -> new EntityNotFoundException("Size not found"));

        OrderCocktail oc = new OrderCocktail();
        oc.setOrder(order);
        oc.setCocktail(cocktail);
        oc.setSize(size);
        oc.setStep(request.getStep());

        OrderCocktail saved = orderCocktailRepository.save(oc);
        return new OrderCocktailResponse(saved.getId(), order.getId(), cocktail.getId(), size.getId(), saved.getStep());
    }

    @Override
    public List<OrderCocktailResponse> getByOrderId(Long orderId) {
        return orderCocktailRepository.findAll().stream()
                .filter(oc -> oc.getOrder().getId().equals(orderId))
                .map(oc -> new OrderCocktailResponse(
                        oc.getId(),
                        oc.getOrder().getId(),
                        oc.getCocktail().getId(),
                        oc.getSize().getId(),
                        oc.getStep()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStep(Long id, OrderCocktailRequest request) {
        OrderCocktail oc = orderCocktailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderCocktail not found"));
        oc.setStep(request.getStep());
        orderCocktailRepository.save(oc);
    }

    @Override
    public void delete(Long id) {
        orderCocktailRepository.deleteById(id);
    }
}
