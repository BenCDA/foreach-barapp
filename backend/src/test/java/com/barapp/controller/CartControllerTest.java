package com.barapp.controller;

import com.barapp.dto.CartResponse;
import com.barapp.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void addToCart_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/cart/add")
                .with(user("ben"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cocktailId\":1,\"sizeId\":2}"))
                .andExpect(status().isOk());
    }

    @Test
    void getCart_shouldReturnOk() throws Exception {
        Mockito.when(cartService.getCart("ben")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cart")
                .with(user("ben")))
                .andExpect(status().isOk());
    }
}
