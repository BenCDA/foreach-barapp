// src/main/java/com/barapp/dto/OrderResponse.java
package com.barapp.dto;

import java.time.LocalDateTime;

import com.barapp.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String userEmail;
    private LocalDateTime orderDate;
    private String status;

    public static OrderResponse fromEntity(Order o) {
        return new OrderResponse(
            o.getId(),
            o.getUser().getEmail(),
            o.getOrderDate(),
            o.getStatus().name()
        );
    }
}
