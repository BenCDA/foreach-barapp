package com.barapp.controller;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.service.CocktailSizePriceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CocktailSizePriceController.class)
public class CocktailSizePriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailSizePriceService service;

    @Test
    void getById_shouldReturnOk() throws Exception {
        Mockito.when(service.getById(1L))
                .thenReturn(new CocktailSizePriceResponse(1L,1L,1L,10));

        mockMvc.perform(get("/api/cocktail-size-prices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        Mockito.when(service.create(any(CocktailSizePriceRequest.class)))
                .thenReturn(new CocktailSizePriceResponse(1L,1L,1L,10));

        mockMvc.perform(post("/api/cocktail-size-prices")
                .with(SecurityMockMvcRequestPostProcessors.user("barman").roles("BARMAN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cocktailId\":1,\"sizeId\":1,\"price\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(10));
    }
}
