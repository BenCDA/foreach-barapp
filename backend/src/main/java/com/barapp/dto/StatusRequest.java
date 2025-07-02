package com.barapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatusRequest {
    @NotBlank
    private String statut; // COMMANDEE, EN_PREPARATION, TERMINEE
}
