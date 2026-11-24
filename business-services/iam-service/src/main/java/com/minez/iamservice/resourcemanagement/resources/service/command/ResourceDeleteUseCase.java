package com.minez.iamservice.resourcemanagement.resources.service.command;

import com.minez.corestarter.template.Command;
import com.minez.corestarter.template.Result;
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
public class ResourceDeleteUseCase extends Command<ResourceUuid, ResourceUuid> {
    ResourceRepository resources;

    @Override
    public Result<ResourceUuid> handle(ResourceUuid command) {
        validateCommand(command);
        UUID resourceUuid = command.resourceUuid();
        resources.deleteByResourceUuid(resourceUuid);
        ResourceUuid result = new ResourceUuid(resourceUuid);
        return Result.value(result);
    }

    private void validateCommand(ResourceUuid command) {
        UUID resourceUuid = command.resourceUuid();
        Boolean exist = resources.existsResourceUuid(resourceUuid);
        if(!exist) {
            throw ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND);
        }
    }
}
