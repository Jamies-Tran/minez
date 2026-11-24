package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceRepository {
    ResourceJpaRepository repository;

    public Boolean existsResourceCode(String resourceCode) {
        return repository.existsByResourceCode(resourceCode);
    }

    public Resource save(Resource resource) {
        ResourceJpaEntity entity = new ResourceJpaEntity();
        entity.applyDomain(resource);
        ResourceJpaEntity saved = repository.save(entity);
        return saved.convertDomain();
    }
}
