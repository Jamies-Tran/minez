package com.minez.iamservice.resourcemanagement.resources.controller;

import com.minez.corestarter.template.ApiResponse;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceCreateRequest;
import com.minez.iamservice.resourcemanagement.resources.controller.model.ResourceResponse;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceCreate;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import com.minez.iamservice.resourcemanagement.resources.service.command.ResourceCreateUseCase;
import com.minez.iamservice.resourcemanagement.resources.service.query.ResourceSearchPageUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/resources")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceController {
    ResourceCreateUseCase createUseCase;
    ResourceSearchPageUseCase searchPageUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<ResourceResponse> save(@RequestBody @Validated ResourceCreateRequest request) {
        ResourceCreate command = ResourceCreate.applyRequest(request);
        Result<Resource> create = createUseCase.handle(command);
        Resource resource = create.data();
        ResourceResponse response = ResourceResponse.applyDomain(resource);
        return ApiResponse.value(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ResourceResponse> findAll(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") List<String> resourceCodes,
            @RequestParam(required = false, defaultValue = "") List<String> resourceTypes,
            @RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> createdAtRange,
            @RequestParam(required = false, defaultValue = "") Boolean isEnabled,
            @RequestParam(required = false, defaultValue = "createdAt_desc") String sorter,
            @RequestParam(required = false, defaultValue = "0") Integer current,
            @RequestParam(required = false, defaultValue = "25") Integer pageSize
    ) {
        ResourceSearch query = new ResourceSearch(search, resourceCodes, resourceTypes, createdAtRange, isEnabled, sorter, current, pageSize);
        Result<Page<Resource>> handle = searchPageUseCase.handle(query);
        Page<Resource> resources = handle.data();
        Page<ResourceResponse> responses = resources.map(ResourceResponse::applyDomain);
        return ApiResponse.page(responses);
    }
}
