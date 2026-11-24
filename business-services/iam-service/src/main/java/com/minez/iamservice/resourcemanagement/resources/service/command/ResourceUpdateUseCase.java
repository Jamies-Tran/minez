package com.minez.iamservice.resourcemanagement.resources.service.command;

import com.minez.corestarter.template.Command;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceUpdate;
import com.minez.iamservice.resourcemanagement.resources.domain.error.ResourceException;
import com.minez.iamservice.resourcemanagement.resources.repository.ResourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceUpdateUseCase extends Command<ResourceUpdate, Resource> {
    ResourceRepository resources;

    @Override
    public Result<Resource> handle(ResourceUpdate command) {
        validateCommand(command);
        UUID resourceUuid = command.resourceUuid();
        Resource newResource = Resource.applyCommand(command);
        Resource updated = update(resourceUuid, newResource);
        return Result.value(updated);
    }

    private void validateCommand(ResourceUpdate command) {
        String resourceType = command.resourceType();
        validateResourceType(resourceType);
        UUID resourceUuid = command.resourceUuid();
        Resource exists = getResource(resourceUuid);
        String existResourceCode = exists.resourceCode();
        String newResourceCode = command.resourceCode();
        validateExistResourceCode(existResourceCode, newResourceCode);
    }

    private Resource update(UUID resourceUuid, Resource newResource) {
        return resources.update(resourceUuid, newResource)
                .orElseThrow(() -> ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND));
    }

    private Resource getResource(UUID resourceUuid) {
        return  resources.findByResourceUuid(resourceUuid)
                .orElseThrow(() -> ResourceException.exception(ResourceException.ExceptionType.NOT_FOUND));
    }

    private void validateResourceType(String resourceType) {
        boolean valid = Stream.of(Resource.ResourceType.values())
                .anyMatch(type -> Objects.equals(type.name().toLowerCase(), resourceType.toLowerCase()));
        if(!valid) {
            throw ResourceException.exception(ResourceException.ExceptionType.INVALID_TYPE);
        }
    }

    private void validateExistResourceCode(String existCode, String newCode) {
        if(!Objects.equals(existCode, newCode)){
            Boolean dup = resources.existsResourceCode(newCode);
            if(dup){
                throw ResourceException.exception(ResourceException.ExceptionType.DUPLICATED);
            }
        }
    }
}
