package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceRepository {
    ResourceJpaRepository repository;

    public Boolean existsResourceCode(String resourceCode) {
        return repository.existsByResourceCode(resourceCode);
    }

    public Boolean existsResourceUuid(UUID resourceUuid) {
        return repository.existsByResourceUuid(resourceUuid);
    }

    public Resource save(Resource resource) {
        ResourceJpaEntity entity = new ResourceJpaEntity();
        entity.applyDomain(resource);
        ResourceJpaEntity saved = repository.save(entity);
        return saved.convertDomain();
    }

    public Page<Resource> findAll(ResourceSearch search, PageRequest pageRequest) {
        Page<ResourceJpaEntity> entity = repository.findAll(search, pageRequest);
        return entity.map(ResourceJpaEntity::convertDomain);
    }

    public Page<Resource> findTopTree(ResourceSearch search, PageRequest pageRequest) {
        Page<ResourceJpaEntity> entity = repository.findTopTree(search, pageRequest);
        return entity.map(ResourceJpaEntity::convertDomain);
    }

    public Optional<Resource> findByResourceUuid(UUID resourceUuid) {
        return repository.findByResourceUuid(resourceUuid)
                .map(ResourceJpaEntity::convertDomain);
    }

    public Optional<Resource> findByResourceCode(String resourceCode) {
        return repository.findByResourceCode(resourceCode)
                .map(ResourceJpaEntity::convertDomain);
    }

    public Optional<Resource> update(UUID resourceUuid, Resource newResource) {
        Optional<ResourceJpaEntity> find = repository.findByResourceUuid(resourceUuid);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        ResourceJpaEntity entity = find.get();
        entity.applyDomain(newResource);
        ResourceJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public Optional<Resource> enable(UUID resourceUuid) {
        Optional<ResourceJpaEntity> find = repository.findByResourceUuid(resourceUuid);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        ResourceJpaEntity entity = find.get();
        entity.setEnable();
        ResourceJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public Optional<Resource> disable(UUID resourceUuid) {
        Optional<ResourceJpaEntity> find = repository.findByResourceUuid(resourceUuid);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        ResourceJpaEntity entity = find.get();
        entity.setDisable();
        ResourceJpaEntity saved = repository.save(entity);
        return Optional.of(saved.convertDomain());
    }

    public void deleteByResourceUuid(UUID resourceUuid) {
        repository.deleteByResourceUuid(resourceUuid);
    }

    public List<Resource> findAllByParentUuidIn(List<UUID> parentUuids) {
        return repository.findAllByParentUuidIn(parentUuids)
                .stream()
                .map(ResourceJpaEntity::convertDomain)
                .toList();
    }
}
