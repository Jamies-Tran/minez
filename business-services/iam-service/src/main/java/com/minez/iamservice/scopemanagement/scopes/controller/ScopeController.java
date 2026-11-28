package com.minez.iamservice.scopemanagement.scopes.controller;

import com.minez.corestarter.template.ApiResponse;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.scopemanagement.scopes.controller.model.ScopeCreateRequest;
import com.minez.iamservice.scopemanagement.scopes.controller.model.ScopeResponse;
import com.minez.iamservice.scopemanagement.scopes.controller.model.ScopeUpdateRequest;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeCreate;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeSearch;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeUpdate;
import com.minez.iamservice.scopemanagement.scopes.domain.argument.ScopeUuid;
import com.minez.iamservice.scopemanagement.scopes.service.command.*;
import com.minez.iamservice.scopemanagement.scopes.service.query.ScopeSearchDetailUseCase;
import com.minez.iamservice.scopemanagement.scopes.service.query.ScopeSearchPageUseCase;
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
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/scopes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScopeController {
    ScopeCreateUseCase createScopeUseCase;
    ScopeSearchPageUseCase searchPageUseCase;
    ScopeSearchDetailUseCase searchDetailUseCase;
    ScopeUpdateUseCase updateUseCase;
    ScopeEnableUseCase enableUseCase;
    ScopeDisableUseCase disableUseCase;
    ScopeDeleteUseCase deleteUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse<ScopeResponse> create(@RequestBody @Validated ScopeCreateRequest request) {
        ScopeCreate command = ScopeCreate.applyRequest(request);
        Result<Scope> create = createScopeUseCase.handle(command);
        Scope scope = create.data();
        ScopeResponse response = ScopeResponse.applyDomain(scope);
        return ApiResponse.value(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScopeResponse> findAll(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") List<String> scopeCodes,
            @RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) List<LocalDateTime> createdAtRange,
            @RequestParam(required = false, defaultValue = "") Boolean isEnabled,
            @RequestParam(required = false, defaultValue = "updatedAt_desc") String sorter,
            @RequestParam(required = false, defaultValue = "0") Integer current,
            @RequestParam(required = false, defaultValue = "25") Integer pageSize
    ) {
        ScopeSearch query = new ScopeSearch(search, scopeCodes, createdAtRange, isEnabled, sorter, current, pageSize);
        Result<Page<Scope>> page = searchPageUseCase.handle(query);
        Page<Scope> scopes =  page.data();
        Page<ScopeResponse> responses = scopes.map(ScopeResponse::applyDomain);
        return ApiResponse.page(responses);
    }

    @GetMapping("/{scopeUuid}")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScopeResponse> findByScopeUuid(@PathVariable UUID scopeUuid) {
        ScopeUuid query = new ScopeUuid(scopeUuid);
        Result<Scope> value = searchDetailUseCase.handle(query);
        Scope scope = value.data();
        ScopeResponse response = ScopeResponse.applyDomain(scope);
        return ApiResponse.value(response);
    }

    @PutMapping("/{scopeUuid}")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScopeResponse> update(@PathVariable UUID scopeUuid, @RequestBody @Validated ScopeUpdateRequest request) {
        ScopeUpdate command = ScopeUpdate.applyRequest(scopeUuid, request);
        Result<Scope> update = updateUseCase.handle(command);
        Scope scope = update.data();
        ScopeResponse response = ScopeResponse.applyDomain(scope);
        return ApiResponse.value(response);
    }

    @PatchMapping("/{scopeUuid}/enable")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScopeResponse> enable(@PathVariable UUID scopeUuid) {
        ScopeUuid command = new ScopeUuid(scopeUuid);
        Result<Scope> enable = enableUseCase.handle(command);
        Scope scope = enable.data();
        ScopeResponse response = ScopeResponse.applyDomain(scope);
        return ApiResponse.value(response);
    }

    @PatchMapping("/{scopeUuid}/disable")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScopeResponse> disable(@PathVariable UUID scopeUuid) {
        ScopeUuid command = new ScopeUuid(scopeUuid);
        Result<Scope> disable = disableUseCase.handle(command);
        Scope scope = disable.data();
        ScopeResponse response = ScopeResponse.applyDomain(scope);
        return ApiResponse.value(response);
    }

    @DeleteMapping("/{scopeUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByScopeUuid(@PathVariable UUID scopeUuid) {
        ScopeUuid command = new ScopeUuid(scopeUuid);
        deleteUseCase.handle(command);
    }
}
