package com.example.resilient_api.domain.spi;

import com.example.resilient_api.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TechnologyPersistencePort {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existByName(String name);
    Flux<Long> findExistingIdsByIds(List<Long> ids);
    Flux<Technology> findAllByIdIn(List<Long> ids);
    Mono<Void> deleteById(Long id);
}

