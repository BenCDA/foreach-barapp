package com.barapp.dto;

import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long orderId;
    private String status;
    private LocalDateTime orderDate;
    private List<CocktailLine> cocktails;

    @Data
    public static class CocktailLine {
        private Long id;
        private String cocktailName;
        private String sizeLabel;
        private String step;
        private Integer price; // le prix du cocktail dans cette taille
    }

    // version SANS accès repository (gardé pour compatibilité)
    public static OrderResponse from(Order order, List<OrderCocktail> lines) {
        OrderResponse resp = new OrderResponse();
        resp.setOrderId(order.getId());
        resp.setStatus(order.getStatus().name());
        resp.setOrderDate(order.getOrderDate());
        List<CocktailLine> cls = lines.stream().map(oc -> {
            CocktailLine cl = new CocktailLine();
            cl.setId(oc.getId());
            cl.setCocktailName(oc.getCocktail().getName());
            cl.setSizeLabel(oc.getSize().getLabel());
            cl.setStep(oc.getStep().name());
            cl.setPrice(null); // pas de prix (pour compatibilité)
            return cl;
        }).collect(Collectors.toList());
        resp.setCocktails(cls);
        return resp;
    }

    // version AVEC repository pour le prix
    public static OrderResponse from(Order order, List<OrderCocktail> lines, com.barapp.repository.CocktailSizePriceRepository priceRepo) {
        OrderResponse resp = new OrderResponse();
        resp.setOrderId(order.getId());
        resp.setStatus(order.getStatus().name());
        resp.setOrderDate(order.getOrderDate());
        List<CocktailLine> cls = lines.stream().map(oc -> {
            CocktailLine cl = new CocktailLine();
            cl.setId(oc.getId());
            cl.setCocktailName(oc.getCocktail().getName());
            cl.setSizeLabel(oc.getSize().getLabel());
            cl.setStep(oc.getStep().name());
            // Trouver le prix de ce cocktail + cette taille
            Integer prix = priceRepo
                .findByCocktailId(oc.getCocktail().getId())
                .stream()
                .filter(p -> p.getSize().getId().equals(oc.getSize().getId()))
                .findFirst()
                .map(com.barapp.model.CocktailSizePrice::getPrice)
                .orElse(0);
            cl.setPrice(prix);
            return cl;
        }).collect(Collectors.toList());
        resp.setCocktails(cls);
        return resp;
    }
}
