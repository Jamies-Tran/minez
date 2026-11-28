package com.minez.iamservice.scopemanagement.scopes.domain.argument;

import com.minez.iamservice.scopemanagement.scopes.controller.model.ScopeCreateRequest;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ScopeCreate(
        @NonNull
        String scopeCode,

        @NonNull
        String scopeName,

        Boolean isEnabled
) {
    public static ScopeCreate applyRequest(ScopeCreateRequest request) {
        return ScopeCreate.builder()
                .scopeCode(request.scopeCode())
                .scopeName(request.scopeName())
                .isEnabled(request.isEnabled())
                .build();
    }
}
