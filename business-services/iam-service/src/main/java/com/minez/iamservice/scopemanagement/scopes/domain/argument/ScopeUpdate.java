package com.minez.iamservice.scopemanagement.scopes.domain.argument;

import com.minez.iamservice.scopemanagement.scopes.controller.model.ScopeUpdateRequest;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ScopeUpdate(
        UUID scopeUuid,
        String scopeCode,
        String scopeName
) {
    public static ScopeUpdate applyRequest(UUID scopeUuid, ScopeUpdateRequest request) {
        return ScopeUpdate.builder()
                .scopeUuid(scopeUuid)
                .scopeCode(request.scopeCode())
                .scopeName(request.scopeName())
                .build();
    }
}
