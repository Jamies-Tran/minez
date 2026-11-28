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
public class ScopeDeleteUseCase extends Command<ScopeUuid, ScopeUuid> {
    ScopeRepository scopes;

    @Override
    public Result<ScopeUuid> handle(ScopeUuid command) {
        validateCommand(command);
        UUID scopeUuid = command.scopeUuid();
        scopes.deleteByScopeUuid(scopeUuid);
        return Result.value(new ScopeUuid(scopeUuid));
    }

    private void validateCommand(ScopeUuid command) {
        UUID scopeUuid = command.scopeUuid();
        Boolean exist = scopes.existsScopeUuid(scopeUuid);
        if (!exist) {
            throw ScopeException.exception(ScopeException.ExceptionType.NOT_FOUND);
        }
    }
}
