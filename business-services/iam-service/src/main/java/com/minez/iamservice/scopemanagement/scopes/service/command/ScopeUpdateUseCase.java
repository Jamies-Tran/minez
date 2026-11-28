package com.minez.iamservice.scopemanagement.scopes.service.command;

import com.minez.corestarter.template.Command;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeUpdate;
import com.minez.iamservice.scopemanagement.scopes.domain.error.ScopeException;
import com.minez.iamservice.scopemanagement.scopes.repository.ScopeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeUpdateUseCase extends Command<ScopeUpdate, Scope> {
    ScopeRepository scopes;

    @Override
    public Result<Scope> handle(ScopeUpdate command) {
        validateCommand(command);
        UUID scopeUuid = command.scopeUuid();
        Scope newScope = Scope.applyCommand(command);
        Scope update = update(scopeUuid, newScope);
        return Result.value(update);
    }

    private void validateCommand(ScopeUpdate command) {
        String scopeCode = command.scopeCode();
        Optional<Scope> exist = getScope(scopeCode);
        if (exist.isPresent()) {
            String newScopeCode = command.scopeCode();
            String existScopeCode = exist.get().scopeCode();
            validateScopeCode(existScopeCode, newScopeCode);
        }
    }

    private Optional<Scope> getScope(String scopeCode) {
        return scopes.findByScopeCode(scopeCode);
    }

    private void validateScopeCode(String existScopeCode, String newScopeCode) {
        if(!Objects.equals(existScopeCode, newScopeCode)) {
            Boolean dup = scopes.existsScopeCode(newScopeCode);
            if(dup) {
                throw ScopeException.exception(ScopeException.ExceptionType.DUPLICATED);
            }
        }
    }

    private Scope update(UUID scopeUuid, Scope newScope) {
        return scopes.update(scopeUuid, newScope)
                .orElseThrow(() -> ScopeException.exception(ScopeException.ExceptionType.NOT_FOUND));
    }
}
