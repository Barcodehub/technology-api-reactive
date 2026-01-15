package com.example.resilient_api.domain.usecase;

import com.example.resilient_api.domain.enums.TechnicalMessage;
import com.example.resilient_api.domain.exceptions.BusinessException;
import com.example.resilient_api.domain.model.Technology;
import com.example.resilient_api.domain.api.TechnologyServicePort;
import com.example.resilient_api.domain.spi.TechnologyPersistencePort;
import reactor.core.publisher.Mono;

public class TechnologyUseCase implements TechnologyServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    private final TechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(TechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Mono<Technology> registerTechnology(Technology technology, String messageId) {
        return validateTechnology(technology)
                .then(technologyPersistencePort.existByName(technology.name()))
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.TECHNOLOGY_ALREADY_EXISTS)))
                .flatMap(exists -> technologyPersistencePort.save(technology));
    }

    private Mono<Void> validateTechnology(Technology technology) {
        if (technology.name() == null || technology.name().trim().isEmpty()) {
            return Mono.error(new BusinessException(TechnicalMessage.TECHNOLOGY_NAME_REQUIRED));
        }
        if (technology.description() == null || technology.description().trim().isEmpty()) {
            return Mono.error(new BusinessException(TechnicalMessage.TECHNOLOGY_DESCRIPTION_REQUIRED));
        }
        if (technology.name().length() > MAX_NAME_LENGTH) {
            return Mono.error(new BusinessException(TechnicalMessage.TECHNOLOGY_NAME_TOO_LONG));
        }
        if (technology.description().length() > MAX_DESCRIPTION_LENGTH) {
            return Mono.error(new BusinessException(TechnicalMessage.TECHNOLOGY_DESCRIPTION_TOO_LONG));
        }
        return Mono.empty();
    }
}

