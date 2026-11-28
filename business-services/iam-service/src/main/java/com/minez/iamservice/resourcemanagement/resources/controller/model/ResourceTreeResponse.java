package com.minez.iamservice.resourcemanagement.resources.controller.model;

import com.minez.iamservice.resourcemanagement.resources.domain.ResourceTree;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
public record ResourceTreeResponse(
        Long resourceId,
        UUID resourceUuid,
        String resourceCode,
        String resourceName,
        String resourceType,
        String description,
        Boolean isEnabled,
        List<ResourceTreeResponse> children
) {
    public static ResourceTreeResponse applyDomain(ResourceTree domain) {
        List<ResourceTreeResponse> children = getChild(domain);
        return ResourceTreeResponse.builder()
                .resourceId(domain.resourceId())
                .resourceUuid(domain.resourceUuid())
                .resourceCode(domain.resourceCode())
                .resourceName(domain.resourceName())
                .resourceType(domain.resourceType())
                .description(domain.description())
                .isEnabled(domain.isEnabled())
                .children(children)
                .build();
    }

    private static List<ResourceTreeResponse> getChild(ResourceTree domain) {
        List<ResourceTree> child = domain.children();
        if(Objects.isNull(child)){
            return List.of();
        }
        return child.stream()
                .map(ResourceTreeResponse::applyDomain)
                .toList();
    }
}
