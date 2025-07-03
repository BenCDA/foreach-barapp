package com.barapp.controller;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.service.OrderCocktailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderCocktailController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderCocktailControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderCocktailService service;

    @Test
    void getByOrder_shouldReturnOk() throws Exception {
        Mockito.when(service.getByOrderId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/order-cocktails/order/1")
                .with(SecurityMockMvcRequestPostProcessors.user("bar").roles("BARMAN")))
                .andExpect(status().isOk());
    }

    @Test
    void updateStep_shouldReturnOk() throws Exception {
        OrderCocktailResponse resp = new OrderCocktailResponse();
        resp.setId(1L);
        resp.setOrderId(1L);
        resp.setCocktailId(1L);
        resp.setStep(com.barapp.model.OrderCocktail.Step.PREPARATION);
        Mockito.when(service.updateStep(eq(1L), any(OrderCocktailRequest.class))).thenReturn(resp);

        mockMvc.perform(patch("/api/order-cocktails/1/step")
                .with(SecurityMockMvcRequestPostProcessors.user("bar").roles("BARMAN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"step\":\"PREPARATION\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
