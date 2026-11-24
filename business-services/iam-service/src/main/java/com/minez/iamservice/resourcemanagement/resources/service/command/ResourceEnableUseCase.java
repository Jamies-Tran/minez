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
public class ResourceEnableUseCase extends Command<ResourceUuid, Resource> {
    ResourceRepository resources;

    @Override
    public Result<Resource> handle(ResourceUuid command) {
        validateCommand(command );
        UUID resourceUuid = command.resourceUuid();
        Resource enabled = enableResource(resourceUuid);
        return Result.value(enabled);
    }

    private void validateCommand(ResourceUuid command) {
        UUID resourceUuid = command.resourceUuid();
        Boolean exist = resources.existsResourceUuid(resourceUuid);
        if(!exist) {
            throw ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND);
        }
    }

    private Resource enableResource(UUID resourceUuid) {
        return resources.enable(resourceUuid)
                .orElseThrow(() -> ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND));
    }
}
