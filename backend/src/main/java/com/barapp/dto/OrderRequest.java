package com.barapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {
    @NotNull
    private Long panierId;
}
