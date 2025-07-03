package com.barapp.service;

import com.barapp.dto.SizeRequest;
import com.barapp.dto.SizeResponse;
import com.barapp.mapper.SizeMapper;
import com.barapp.model.Size;
import com.barapp.repository.SizeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SizeServiceTest {

    private SizeRepository repo;
    private SizeService service;

    @BeforeEach
    void setUp() {
        repo = mock(SizeRepository.class);
        service = new SizeService(repo, new SizeMapper());
    }

    @Test
    void create_shouldReturnResponse() {
        SizeRequest req = new SizeRequest();
        req.setLabel("M");
        Size saved = new Size(1L, "M");
        when(repo.save(any(Size.class))).thenReturn(saved);

        SizeResponse resp = service.create(req);

        assertEquals("M", resp.getLabel());
        verify(repo).save(any(Size.class));
    }

    @Test
    void findAll_shouldReturnList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        List<SizeResponse> list = service.findAll();
        assertTrue(list.isEmpty());
    }
}
