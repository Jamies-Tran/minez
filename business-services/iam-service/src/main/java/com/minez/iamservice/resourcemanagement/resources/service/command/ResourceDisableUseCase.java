package com.minez.iamservice.resourcemanagement.resources.service.command;

import com.minez.corestarter.template.Command;
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
public class ResourceDisableUseCase extends Command<ResourceUuid, Resource> {
    ResourceRepository resources;

    @Override
    public Result<Resource> handle(ResourceUuid command) {
        UUID resourceUuid = command.resourceUuid();
        Resource disabled = disableResource(resourceUuid);
        return Result.value(disabled);
    }

    private Resource disableResource(UUID resourceUuid) {
        return resources.disable(resourceUuid)
                .orElseThrow(() -> ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND));
    }
}
