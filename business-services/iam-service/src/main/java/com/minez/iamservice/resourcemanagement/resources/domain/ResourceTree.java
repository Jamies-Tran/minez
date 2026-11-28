package com.minez.iamservice.resourcemanagement.resources.domain;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public record ResourceTree(
        Long resourceId,
        UUID resourceUuid,
        UUID parentUuid,
        String resourceCode,
        String resourceName,
        String resourceType,
        String description,
        Boolean isEnabled,
        List<ResourceTree> children
) {


    public static ResourceTree apply(Resource resource) {
        return ResourceTree.builder()
                .resourceId(resource.resourceId())
                .parentUuid(resource.parentUuid())
                .resourceUuid(resource.resourceUuid())
                .resourceCode(resource.resourceCode())
                .resourceName(resource.resourceName())
                .resourceType(resource.resourceType())
                .description(resource.description())
                .isEnabled(resource.isEnabled())
                .children(new ArrayList<>())
                .build();
    }

    public static ResourceTree apply(Resource resource, List<ResourceTree> children) {
        return ResourceTree.builder()
                .resourceId(resource.resourceId())
                .parentUuid(resource.parentUuid())
                .resourceUuid(resource.resourceUuid())
                .resourceCode(resource.resourceCode())
                .resourceName(resource.resourceName())
                .resourceType(resource.resourceType())
                .description(resource.description())
                .isEnabled(resource.isEnabled())
                .children(children)
                .build();
    }
}
