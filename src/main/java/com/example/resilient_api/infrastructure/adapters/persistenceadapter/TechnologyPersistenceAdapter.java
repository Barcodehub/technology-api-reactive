package com.example.resilient_api.infrastructure.adapters.persistenceadapter;

import com.example.resilient_api.domain.model.Technology;
import com.example.resilient_api.domain.spi.TechnologyPersistencePort;
import com.example.resilient_api.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import com.example.resilient_api.infrastructure.adapters.persistenceadapter.mapper.TechnologyEntityMapper;
import com.example.resilient_api.infrastructure.adapters.persistenceadapter.repository.TechnologyRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public class TechnologyPersistenceAdapter implements TechnologyPersistencePort {
    private final TechnologyRepository technologyRepository;
    private final TechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository.save(technologyEntityMapper.toEntity(technology))
                .map(technologyEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return technologyRepository.findByName(name)
                .map(technologyEntityMapper::toModel)
                .map(technology -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Flux<Long> findExistingIdsByIds(List<Long> ids) {
        return technologyRepository.findAllByIdIn(ids)
                .map(TechnologyEntity::getId);
    }
}


