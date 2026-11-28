package com.minez.iamservice.scopemanagement.scopes.service.command;

import com.minez.corestarter.template.Command;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeCreate;
import com.minez.iamservice.scopemanagement.scopes.domain.error.ScopeException;
import com.minez.iamservice.scopemanagement.scopes.repository.ScopeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeCreateUseCase extends Command<ScopeCreate, Scope> {
    ScopeRepository scopes;

    @Override
    public Result<Scope> handle(ScopeCreate command) {
        validateCommand(command);
        Scope scope = Scope.applyCommand(command);
        Scope saved = scopes.save(scope);
        return Result.value(saved);
    }

    private void validateCommand(ScopeCreate command) {
        String scopeCode = command.scopeCode();
        Boolean dup = scopes.existsScopeCode(scopeCode);
        if (dup) {
            throw ScopeException.exception(ScopeException.ExceptionType.NOT_FOUND);
        }
    }
}
