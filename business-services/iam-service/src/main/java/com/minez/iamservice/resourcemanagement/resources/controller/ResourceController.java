package com.minez.iamservice.resourcemanagement.resources.controller;

import com.minez.corestarter.template.ApiResponse;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceCreateRequest;
import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceResponse;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceCreate;
import com.minez.iamservice.resourcemanagement.resources.service.command.ResourceCreateUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/resources")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceController {
    ResourceCreateUseCase createUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<ResourceResponse> save(@RequestBody @Validated ResourceCreateRequest request) {
        ResourceCreate command = ResourceCreate.applyRequest(request);
        Result<Resource> create = createUseCase.handle(command);
        Resource resource = create.data();
        ResourceResponse response = ResourceResponse.applyDomain(resource);
        return ApiResponse.value(response);
    }
}
