package com.minez.iamservice.scopemanagement.scopes.service.query;

import com.minez.corestarter.template.Query;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeSearch;
import com.minez.iamservice.scopemanagement.scopes.repository.ScopeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeSearchPageUseCase extends Query<ScopeSearch, Page<Scope>> {
    ScopeRepository scopes;

    @Override
    public Result<Page<Scope>> handle(ScopeSearch query) {
        PageRequest pageRequest = query.pageRequest();
        Page<Scope> page = scopes.findAll(query, pageRequest);
        return Result.page(page);
    }
}
