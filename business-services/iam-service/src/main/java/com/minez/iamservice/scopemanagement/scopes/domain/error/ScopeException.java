package com.minez.iamservice.scopemanagement.scopes.domain.error;

import com.minez.corestarter.advice.ApplicationException;
import com.minez.corestarter.template.ApplicationErrorCode;
import com.minez.iamservice.resourcemanagement.resources.domain.error.ResourceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

public class ScopeException {
    public static ApplicationException exception(ExceptionType exceptionType) {
        return switch (exceptionType) {
            case DUPLICATED -> new ApplicationException(ExceptionCode.SCOPE_DUPLICATE, ExceptionCode.SCOPE_DUPLICATE.getMessage(), HttpStatus.BAD_REQUEST);
            case NOT_FOUND -> new ApplicationException(ExceptionCode.SCOPE_NOT_FOUND, ExceptionCode.SCOPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        };
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    enum ExceptionCode implements ApplicationErrorCode {
        SCOPE_NOT_FOUND("SC_001", "Scope not found"),
        SCOPE_DUPLICATE("SC_002", "Scope already exists"),;

        String code;
        String message;
    }

    public enum ExceptionType {
        DUPLICATED,
        NOT_FOUND
    }
}
