package com.minez.iamservice.resourcemanagement.resources.service.query;

import com.minez.corestarter.template.Query;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceUuid;
import com.minez.iamservice.resourcemanagement.resources.domain.error.ResourceException;
import com.minez.iamservice.resourcemanagement.resources.repository.ResourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceSearchDetailUseCase extends Query<ResourceUuid, Resource> {
    ResourceRepository resources;

    @Override
    public Result<Resource> handle(ResourceUuid query) {
        Resource resource = getResource(query);
        return Result.value(resource);
    }

    private Resource getResource(ResourceUuid query) {
        UUID resourceUuid = query.resourceUuid();
        return resources.findByResourceUuid(resourceUuid)
                .orElseThrow(() -> ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND));
    }
}
