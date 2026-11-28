package com.minez.iamservice.scopemanagement.scopes.service.command;

import com.minez.corestarter.template.Command;
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
public class ScopeDisableUseCase extends Command<ScopeUuid, Scope> {
    ScopeRepository scopes;

    @Override
    public Result<Scope> handle(ScopeUuid command) {
        UUID scopeUuid = command.scopeUuid();
        Scope disable = disable(scopeUuid);
        return Result.value(disable);
    }

    private Scope disable(UUID scopeUuid) {
        return scopes.disable(scopeUuid)
                .orElseThrow(() -> ScopeException.exception(ScopeException.ExceptionType.NOT_FOUND));
    }
}
