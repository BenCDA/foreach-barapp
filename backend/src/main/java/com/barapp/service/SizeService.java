package com.barapp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.barapp.dto.SizeRequest;
import com.barapp.dto.SizeResponse;
import com.barapp.mapper.SizeMapper;
import com.barapp.model.Size;
import com.barapp.repository.SizeRepository;

@Service
@Transactional
public class SizeService {

    private final SizeRepository repo;
    private final SizeMapper mapper;

    public SizeService(SizeRepository repo, SizeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public SizeResponse create(SizeRequest req) {
        Size saved = repo.save(mapper.toEntity(req));
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SizeResponse> findAll() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toDto)
                   .collect(Collectors.toList());
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
