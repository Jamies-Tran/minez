package com.minez.iamservice.scopemanagement.scopes.repository;

import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface ScopeJpaRepository extends JpaRepository<ScopeJpaEntity, Long> {
    Boolean existsByScopeCode(String scopeCode);

    Boolean existsByScopeUuid(UUID scopeUuid);

    Optional<ScopeJpaEntity> findByScopeUuid(UUID scopeUuid);

    Optional<ScopeJpaEntity> findByScopeCode(String scopeCode);

    @Modifying
    void deleteByScopeUuid(UUID scopeUuid);

    @Query("""
        SELECT s
        FROM Scope s
        WHERE (:#{#search.searchEmpty()} = TRUE
                OR s.search LIKE %:#{#search.search()}%)
        AND (:#{#search.scopeCodesEmpty()} = TRUE
                OR s.scopeCode IN :#{#search.scopeCodes()})
        AND (:#{#search.isEnabledEmpty()} = TRUE
                OR s.isEnabled = :#{#search.isEnabled()})
        AND (:#{#search.createdAtRangeEmpty()} = TRUE
                OR s.createdAt BETWEEN :#{#search.createdAtRangeBegin()} AND :#{#search.createdAtRangeEnd()})
        """)
    Page<ScopeJpaEntity> findAll(ScopeSearch search, Pageable pageable);
}
