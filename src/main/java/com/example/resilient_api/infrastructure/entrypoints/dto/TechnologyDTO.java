package com.example.resilient_api.infrastructure.entrypoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TechnologyDTO {
    private Long id;
    private String name;
    private String description;
    private Long capacityId;
}
