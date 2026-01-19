package com.example.resilient_api.infrastructure.entrypoints;

import com.example.resilient_api.infrastructure.entrypoints.handler.TechnologyHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(TechnologyHandlerImpl technologyHandler) {
        return route(POST("/technology"), technologyHandler::createTechnology)
            .andRoute(POST("/technology/check-exists"), technologyHandler::checkTechnologiesExist)
            .andRoute(POST("/technology/by-ids"), technologyHandler::getTechnologiesByIds)
            .andRoute(POST("/technology/decrement-references"), technologyHandler::decrementTechnologyReferences);
    }

}
