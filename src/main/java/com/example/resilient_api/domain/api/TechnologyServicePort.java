package com.example.resilient_api.domain.api;

import com.example.resilient_api.domain.model.Technology;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface TechnologyServicePort {
    Mono<Technology> registerTechnology(Technology technology, String messageId);
    Mono<Map<Long, Boolean>> checkTechnologiesExist(List<Long> ids, String messageId);
}
