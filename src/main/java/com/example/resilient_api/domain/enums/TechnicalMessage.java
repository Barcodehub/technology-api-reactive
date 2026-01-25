package com.example.resilient_api.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {

    INTERNAL_ERROR("500","Something went wrong, please try again", ""),
    INVALID_REQUEST("400", "Bad Request, please verify data", ""),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Bad Parameters, please verify data", ""),
    UNSUPPORTED_OPERATION("501", "Method not supported, please try again", ""),
    TECHNOLOGY_CREATED("201", "Technology created successfully", ""),
    TECHNOLOGY_ALREADY_EXISTS("400", "Technology with this name already exists", "name"),
    TECHNOLOGY_NAME_REQUIRED("400", "Technology name is required", "name"),
    TECHNOLOGY_DESCRIPTION_REQUIRED("400", "Technology description is required", "description"),
    TECHNOLOGY_NAME_TOO_LONG("400", "Technology name cannot exceed 50 characters", "name"),
    TECHNOLOGY_DESCRIPTION_TOO_LONG("400", "Technology description cannot exceed 90 characters", "description"),
    TOKEN_EXPIRED("401", "JWT token has expired", "token"),
    TOKEN_INVALID("401", "JWT token is invalid", "token")
    ;

    private final String code;
    private final String message;
    private final String param;
}