package com.barapp.controller;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.dto.StatusRequest;
import com.barapp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.barapp.TestSecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_shouldReturnCreated() throws Exception {
        Mockito.when(orderService.create(Mockito.eq("ben@test.com"), any(OrderRequest.class)))
                .thenReturn(new OrderResponse());

        mockMvc.perform(post("/api/orders")
                .with(SecurityMockMvcRequestPostProcessors.user("ben@test.com").roles("CLIENT"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void toTreat_shouldReturnOk() throws Exception {
        Mockito.when(orderService.getByStatusNotFinished()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders/to-treat")
                .with(SecurityMockMvcRequestPostProcessors.user("barman").roles("BARMAN")))
                .andExpect(status().isOk());
    }
}
