package com.barapp.controller;

import com.barapp.dto.SizeRequest;
import com.barapp.dto.SizeResponse;
import com.barapp.service.SizeService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SizeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SizeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SizeService sizeService;

    @Test
    void listSizes_shouldReturnOk() throws Exception {
        Mockito.when(sizeService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/sizes")
                .with(SecurityMockMvcRequestPostProcessors.user("client").roles("CLIENT")))
                .andExpect(status().isOk());
    }

    @Test
    void createSize_shouldReturnCreated() throws Exception {
        Mockito.when(sizeService.create(any(SizeRequest.class)))
                .thenReturn(new SizeResponse(1L, "M"));

        mockMvc.perform(post("/api/sizes")
                .with(SecurityMockMvcRequestPostProcessors.user("barman").roles("BARMAN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"label\":\"M\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.label").value("M"));
    }
}
