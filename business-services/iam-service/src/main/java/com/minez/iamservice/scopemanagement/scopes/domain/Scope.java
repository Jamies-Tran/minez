package com.minez.iamservice.scopemanagement.scopes.domain;

import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeCreate;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeUpdate;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Scope(
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
    public static Scope applyCommand(ScopeCreate command) {
        return Scope.builder()
                .scopeCode(command.scopeCode())
                .scopeName(command.scopeName())
                .isEnabled(command.isEnabled())
                .build();
    }

    public static Scope applyCommand(ScopeUpdate command) {
        return Scope.builder()
                .scopeCode(command.scopeCode())
                .scopeName(command.scopeName())
                .build();
    }
}
