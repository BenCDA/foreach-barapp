package com.barapp.dto;

import java.time.LocalDateTime;

import com.barapp.model.Order.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private Status status;
}
