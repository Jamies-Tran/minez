package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ResourceJpaRepository extends JpaRepository<ResourceJpaEntity, Long> {
    Boolean existsByResourceCode(String resourceCode);

    @Query("""
        SELECT r
        FROM Resource r
        WHERE (:#{#search.searchEmpty()} = TRUE
                OR r.search LIKE %:#{#search.search()}%)
        AND (:#{#search.resourceCodesEmpty()} = TRUE
                OR r.resourceCode IN :#{#search.resourceCodes()})
        AND (:#{#search.resourceTypesEmpty()} = TRUE
                OR r.resourceType IN :#{#search.resourceTypes()})
        AND (:#{#search.createdAtRangeEmpty()} = TRUE
                OR r.createdAt BETWEEN :#{#search.createdAtRangeBegin()}
                        AND :#{#search.createdAtRangeEnd()})
        AND (:#{#search.isEnabledEmpty()} = TRUE
                OR r.isEnabled = :#{#search.isEnabled()})
        """)
    Page<ResourceJpaEntity> findAll(ResourceSearch search, Pageable pageable);
}
