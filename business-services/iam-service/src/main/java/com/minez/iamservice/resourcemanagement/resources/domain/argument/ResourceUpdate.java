package com.minez.iamservice.resourcemanagement.resources.domain.argument;

import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceUpdateRequest;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ResourceUpdate(
        UUID resourceUuid,
        UUID parentUuid,
        String resourceCode,
        String resourceName,
        String resourceType,
        String description
) {
    public static ResourceUpdate applyRequest(UUID resourceUuid, ResourceUpdateRequest request) {
        return ResourceUpdate.builder()
                .resourceUuid(resourceUuid)
                .parentUuid(request.parentUuid())
                .resourceCode(request.resourceCode())
                .resourceName(request.resourceName())
                .resourceType(request.resourceType())
                .description(request.description())
                .build();
    }
}
