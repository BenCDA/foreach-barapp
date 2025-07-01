package com.barapp.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long cocktailId;
    private Long sizeId;
}
