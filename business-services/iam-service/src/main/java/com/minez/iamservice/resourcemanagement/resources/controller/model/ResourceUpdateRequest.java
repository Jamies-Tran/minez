package com.minez.iamservice.resourcemanagement.resources.controller.model;

import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record ResourceUpdateRequest(
        UUID parentUuid,

        @Length(max = 100, message = "resourceCode exceed maximum length")
        String resourceCode,

        @Length(max = 526, message = "resourceName exceed maximum length")
        String resourceName,

        @Length(max = 100, message = "resourceType exceed maximum length")
        String resourceType,

        @Length(max = 4000, message = "description exceed maximum length")
        String description
) {
}
