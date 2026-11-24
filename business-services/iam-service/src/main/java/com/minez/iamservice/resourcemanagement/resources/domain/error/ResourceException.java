package com.minez.iamservice.resourcemanagement.resources.domain.error;

import com.minez.corestarter.advice.ApplicationException;
import com.minez.corestarter.template.ApplicationErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

public class ResourceException {
    public static ApplicationException exception(ExceptionType exceptionType) {
        return switch (exceptionType) {
            case DUPLICATED -> new ApplicationException(ExceptionCode.RESOURCE_DUPLICATE, ExceptionCode.RESOURCE_DUPLICATE.getMessage(), HttpStatus.BAD_REQUEST);
            case NOT_FOUND -> new ApplicationException(ExceptionCode.RESOURCE_NOT_FOUND, ExceptionCode.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            case INVALID_TYPE ->  new ApplicationException(ExceptionCode.RESOURCE_INVALID_TYPE, ExceptionCode.RESOURCE_INVALID_TYPE.getMessage(), HttpStatus.BAD_REQUEST);
        };
    }

    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    enum ExceptionCode implements ApplicationErrorCode {
        RESOURCE_NOT_FOUND("RE_001", "Resource not found"),
        RESOURCE_DUPLICATE("RE_002", "Resource already exists"),
        RESOURCE_INVALID_TYPE("RE_003", "Resource type invalid"),;

        String code;
        String message;
    }

    public enum ExceptionType {
        DUPLICATED,
        NOT_FOUND,
        INVALID_TYPE;
    }
}
