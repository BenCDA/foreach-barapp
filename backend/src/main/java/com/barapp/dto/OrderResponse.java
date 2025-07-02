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
    }

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
            return cl;
        }).collect(Collectors.toList());
        resp.setCocktails(cls);
        return resp;
    }
}
