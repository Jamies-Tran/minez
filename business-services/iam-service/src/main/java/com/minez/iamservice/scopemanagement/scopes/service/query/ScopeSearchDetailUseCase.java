package com.minez.iamservice.scopemanagement.scopes.service.query;

import com.minez.corestarter.template.Query;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeUuid;
import com.minez.iamservice.scopemanagement.scopes.domain.error.ScopeException;
import com.minez.iamservice.scopemanagement.scopes.repository.ScopeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeSearchDetailUseCase extends Query<ScopeUuid, Scope> {
    ScopeRepository scopes;

    @Override
    public Result<Scope> handle(ScopeUuid query) {
        UUID scopeUuid = query.scopeUuid();
        Scope scope = getScope(scopeUuid);
        return Result.value(scope);
    }

    private Scope getScope(UUID scopeUuid) {
        return scopes.findByScopeUuid(scopeUuid)
                .orElseThrow(() -> ScopeException.exception(ScopeException.ExceptionType.NOT_FOUND));
    }
}
