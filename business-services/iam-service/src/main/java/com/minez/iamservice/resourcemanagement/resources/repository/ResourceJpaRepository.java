package com.minez.iamservice.resourcemanagement.resources.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface ResourceJpaRepository extends JpaRepository<ResourceJpaEntity, Long> {
    Boolean existsByResourceCode(String resourceCode);
}
