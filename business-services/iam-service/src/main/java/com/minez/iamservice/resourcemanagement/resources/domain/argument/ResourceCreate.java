package com.minez.iamservice.resourcemanagement.resources.domain.argument;

import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceCreateRequest;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

@Builder
public record ResourceCreate(
        UUID parentUuid,
        @NonNull
        String resourceCode,
        @NonNull
        String resourceName,
        @NonNull
        String resourceType,
        String description,
        Boolean isEnabled
) {
        public static ResourceCreate applyRequest(ResourceCreateRequest request) {
                return ResourceCreate.builder()
                        .parentUuid(request.parentUuid())
                        .resourceCode(request.resourceCode())
                        .resourceName(request.resourceName())
                        .resourceType(request.resourceType())
                        .description(request.description())
                        .isEnabled(request.isEnabled())
                        .build();
        }
}
