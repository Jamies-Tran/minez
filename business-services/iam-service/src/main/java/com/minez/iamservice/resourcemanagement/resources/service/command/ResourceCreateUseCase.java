package com.minez.iamservice.resourcemanagement.resources.service.command;

import com.minez.corestarter.template.Command;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceCreate;
import com.minez.iamservice.resourcemanagement.resources.domain.error.ResourceException;
import com.minez.iamservice.resourcemanagement.resources.repository.ResourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceCreateUseCase extends Command<ResourceCreate, Resource> {
    ResourceRepository resources;

    @Override
    public Result<Resource> handle(ResourceCreate command) {
        validateCommand(command);
        Resource resource = Resource.applyCommand(command);
        Resource saved = resources.save(resource);
        return Result.value(saved);
    }

    private void validateCommand(ResourceCreate command) {
        String resourceCode = command.resourceCode();
        validateResourceCode(resourceCode);
        String resourceType = command.resourceType();
        validateResourceType(resourceType);
    }

    private void validateResourceCode(String resourceCode) {
        Boolean dup = resources.existsResourceCode(resourceCode);
        if (dup) {
            throw ResourceException.exception(ResourceException.ExceptionType.DUPLICATED);
        }
    }

    private void validateResourceType(String resourceType) {
        boolean valid = Stream.of(Resource.ResourceType.values())
                .anyMatch(type -> Objects.equals(resourceType.toLowerCase(), type.name().toLowerCase()));
        if (!valid) {
            throw ResourceException.exception(ResourceException.ExceptionType.INVALID_TYPE);
        }
    }
}
