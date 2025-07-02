package com.barapp.mapper;

import org.springframework.stereotype.Component;
import com.barapp.dto.SizeRequest;
import com.barapp.dto.SizeResponse;
import com.barapp.model.Size;

@Component
public class SizeMapper {

    public Size toEntity(SizeRequest req) {
        if (req == null) return null;
        Size s = new Size();
        s.setLabel(req.getLabel());
        return s;
    }

    public SizeResponse toDto(Size entity) {
        if (entity == null) return null;
        return new SizeResponse(entity.getId(), entity.getLabel());
    }
}
