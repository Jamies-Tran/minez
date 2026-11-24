package com.minez.iamservice.resourcemanagement.resources.domain;

import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceCreate;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceUpdate;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Resource(
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
    public static Resource applyCommand(ResourceCreate command) {
        return Resource.builder()
                .parentUuid(command.parentUuid())
                .resourceCode(command.resourceCode())
                .resourceName(command.resourceName())
                .resourceType(command.resourceType())
                .description(command.description())
                .isEnabled(command.isEnabled())
                .build();
    }

    public static Resource applyCommand(ResourceUpdate command) {
        return Resource.builder()
                .parentUuid(command.parentUuid())
                .resourceCode(command.resourceCode())
                .resourceName(command.resourceName())
                .resourceType(command.resourceType())
                .description(command.description())
                .build();
    }

    public enum ResourceType {
        MENU,
        API,
        DATA,
        REPORT,
        MODULE,
        FILE
    }
}
