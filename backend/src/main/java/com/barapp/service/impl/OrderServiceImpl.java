package com.barapp.service.impl;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.dto.StatusRequest;
import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import com.barapp.model.User;
import com.barapp.repository.CartRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.UserRepository;
import com.barapp.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository          userRepository;
    private final CartRepository          cartRepository;
    private final OrderRepository         orderRepository;
    private final OrderCocktailRepository orderCocktailRepository;

    @Override
    public OrderResponse create(String userEmail, OrderRequest request) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        // Crée et sauve la commande
        Order draft = new Order();
        draft.setUser(user);
        draft.setOrderDate(LocalDateTime.now());
        draft.setStatus(Order.Status.COMMANDEE);
        Order savedOrder = orderRepository.save(draft);

        // Transforme chaque item du panier en ligne de commande
        List<OrderCocktail> lines = cartRepository.findByUser(user).stream()
            .map(ci -> {
                OrderCocktail oc = new OrderCocktail();
                oc.setOrder(savedOrder);
                oc.setCocktail(ci.getCocktail());
                oc.setSize(ci.getSize());
                oc.setStep(OrderCocktail.Step.PREPARATION);
                return orderCocktailRepository.save(oc);
            })
            .collect(Collectors.toList());

        // Vide le panier
        cartRepository.deleteByUser(user);

        // Retourne le DTO
        return OrderResponse.from(savedOrder, lines);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        return orderRepository.findByUser(user).stream()
            .map(o -> {
                List<OrderCocktail> lines = orderCocktailRepository.findByOrder(o);
                return OrderResponse.from(o, lines);
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getByIdAndUserEmail(Long orderId, String userEmail) {
        Order o = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        if (!o.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Accès refusé");
        }
        List<OrderCocktail> lines = orderCocktailRepository.findByOrder(o);
        return OrderResponse.from(o, lines);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getByStatusNotFinished() {
        // pour l’instant on renvoie tout, tu peux filtrer par o.getStatus()!=TERMINEE
        return orderRepository.findAll().stream()
            .map(o -> {
                List<OrderCocktail> lines = orderCocktailRepository.findByOrder(o);
                return OrderResponse.from(o, lines);
            })
            .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateStatus(Long orderId, StatusRequest request) {
        Order o = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        o.setStatus(Order.Status.valueOf(request.getStatut()));
        Order updated = orderRepository.save(o);
        List<OrderCocktail> lines = orderCocktailRepository.findByOrder(updated);
        return OrderResponse.from(updated, lines);
    }
}
