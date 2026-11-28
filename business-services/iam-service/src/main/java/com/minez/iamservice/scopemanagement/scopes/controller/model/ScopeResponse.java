package com.minez.iamservice.scopemanagement.scopes.controller.model;

import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ScopeResponse(
        Long scopeId,
        UUID scopeUuid,
        String scopeCode,
        String scopeName,
        Boolean isEnabled,
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt
) {
    public static ScopeResponse applyDomain(Scope domain) {
        return ScopeResponse.builder()
                .scopeId(domain.scopeId())
                .scopeUuid(domain.scopeUuid())
                .scopeCode(domain.scopeCode())
                .scopeName(domain.scopeName())
                .isEnabled(domain.isEnabled())
                .createdBy(domain.createdBy())
                .createdAt(domain.createdAt())
                .updatedBy(domain.updatedBy())
                .updatedAt(domain.updatedAt())
                .build();
    }
}
