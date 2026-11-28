package com.minez.iamservice.scopemanagement.scopes.repository;

import com.minez.corestarter.utils.StringConvertUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Objects;

class ScopeEntityListener {
    @PrePersist
    public void prePersist(ScopeJpaEntity entity) {
        Boolean isEnabled = entity.getIsEnabled();
        String scopeCode = StringConvertUtils.normalizeWhiteSpace(entity.getScopeCode());
        String scopeName = StringConvertUtils.normalizeWhiteSpace(entity.getScopeName());
        String search = "%s%s".formatted(scopeCode, scopeName);
        entity.setSearch(search);
        if (Objects.isNull(isEnabled)) {
            entity.setIsEnabled(true);
        }
    }

    @PreUpdate
    public void preUpdate(ScopeJpaEntity entity) {
        String scopeCode = StringConvertUtils.normalizeWhiteSpace(entity.getScopeCode());
        String scopeName = StringConvertUtils.normalizeWhiteSpace(entity.getScopeName());
        String search = "%s%s".formatted(scopeCode, scopeName);
        entity.setSearch(search);
    }
}
