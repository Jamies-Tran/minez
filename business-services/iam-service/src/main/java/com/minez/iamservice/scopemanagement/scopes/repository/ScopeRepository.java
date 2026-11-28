package com.minez.iamservice.scopemanagement.scopes.repository;

import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeSearch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeRepository {
    ScopeJpaRepository repository;

    public Boolean existsScopeCode(String scopeCode) {
        return repository.existsByScopeCode(scopeCode);
    }

    public Boolean existsScopeUuid(UUID scopeUuid) {
        return repository.existsByScopeUuid(scopeUuid);
    }

    public Scope save(Scope scope) {
        ScopeJpaEntity entity = new ScopeJpaEntity();
        entity.applyDomain(scope);
        ScopeJpaEntity saved = repository.save(entity);
        return saved.convertDomain();
    }

    public Page<Scope> findAll(ScopeSearch search, PageRequest pageRequest) {
        return repository.findAll(search, pageRequest)
                .map(ScopeJpaEntity::convertDomain);
    }

    public Optional<Scope> findByScopeUuid(UUID scopeUuid) {
        return repository.findByScopeUuid(scopeUuid)
                .map(ScopeJpaEntity::convertDomain);
    }

    public Optional<Scope> findByScopeCode(String scopeCode) {
        return repository.findByScopeCode(scopeCode)
                .map(ScopeJpaEntity::convertDomain);
    }

    public Optional<Scope> update(UUID scopeUuid, Scope newScope) {
        Optional<ScopeJpaEntity> find = repository.findByScopeUuid(scopeUuid);
        if(find.isEmpty()) {
            return Optional.empty();
        }
        ScopeJpaEntity entity = find.get();
        entity.applyDomain(newScope);
        ScopeJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public Optional<Scope> enable(UUID scopeUuid) {
        Optional<ScopeJpaEntity> find = repository.findByScopeUuid(scopeUuid);
        if(find.isEmpty()) {
            return Optional.empty();
        }
        ScopeJpaEntity entity = find.get();
        entity.enable();
        ScopeJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public Optional<Scope> disable(UUID scopeUuid) {
        Optional<ScopeJpaEntity> find = repository.findByScopeUuid(scopeUuid);
        if(find.isEmpty()) {
            return Optional.empty();
        }
        ScopeJpaEntity entity = find.get();
        entity.disable();
        ScopeJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public void deleteByScopeUuid(UUID scopeUuid) {
        repository.deleteByScopeUuid(scopeUuid);
    }
}
