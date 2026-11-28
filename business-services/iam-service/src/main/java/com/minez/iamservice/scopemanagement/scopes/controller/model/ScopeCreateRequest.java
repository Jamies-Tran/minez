package com.minez.iamservice.scopemanagement.scopes.controller.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ScopeCreateRequest(
        @NotBlank(message = "scopeCode must not be null")
        @Length(max = 100, message = "scopeCode exceed maximum length")
        String scopeCode,

        @NotBlank(message = "scopeName must not be null")
        @Length(max = 526, message = "scopeName exceed maximum length")
        String scopeName,

        Boolean isEnabled
) {
}
