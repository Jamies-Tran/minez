package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ResourceJpaRepository extends JpaRepository<ResourceJpaEntity, Long> {
    Boolean existsByResourceCode(String resourceCode);

    Boolean existsByResourceUuid(UUID resourceUuid);

    Optional<ResourceJpaEntity> findByResourceUuid(UUID resourceUuid);

    Optional<ResourceJpaEntity> findByResourceCode(String resourceCode);

    @Query("""
        SELECT r
        FROM Resource r
        WHERE r.parentUuid IN :parentUuids
                OR EXISTS (
                        SELECT 1
                        FROM Resource r2
                        WHERE r.parentUuid = r2.resourceUuid AND r.parentUuid IS NOT NULL
                        )
        """)
    List<ResourceJpaEntity> findAllByParentUuidIn(List<UUID> parentUuids);

    @Modifying
    void deleteByResourceUuid(UUID resourceUuid);

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
        AND r.parentUuid IS NULL
        """)
    Page<ResourceJpaEntity> findTopTree(ResourceSearch search, Pageable pageable);
}
