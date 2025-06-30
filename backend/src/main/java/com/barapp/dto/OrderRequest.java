package com.barapp.dto;

import com.barapp.model.Order.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {
    @NotNull
    private Long userId;

    private Status status = Status.ORDERED; // valeur par défaut
}
