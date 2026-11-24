package com.minez.iamservice.resourcemanagement.resources.controller.model;

import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ResourceResponse(
        Long resourceId,
        UUID parentUuid,
        UUID resourceUuid,
        String resourceCode,
        String resourceName,
        String resourceType,
        String description,
        Boolean isEnabled,
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt
) {
    public static ResourceResponse applyDomain(Resource domain) {
        return ResourceResponse.builder()
                .resourceId(domain.resourceId())
                .parentUuid(domain.parentUuid())
                .resourceUuid(domain.resourceUuid())
                .resourceCode(domain.resourceCode())
                .resourceName(domain.resourceName())
                .resourceType(domain.resourceType())
                .description(domain.description())
                .isEnabled(domain.isEnabled())
                .createdBy(domain.createdBy())
                .createdAt(domain.createdAt())
                .updatedBy(domain.updatedBy())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
