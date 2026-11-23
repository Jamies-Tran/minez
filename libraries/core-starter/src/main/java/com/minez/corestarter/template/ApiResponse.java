package com.minez.corestarter.template;

import com.minez.corestarter.advice.ApplicationException;
import lombok.Builder;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiResponse<T>(
        String traceId,
        Boolean isSuccess,
        String dataType,
        LocalDateTime responseAt,
        List<String> messages,
        String errorCode,
        Metadata metadata,
        T value,
        List<T> data
) {

    enum EnumDataType {
        EMPTY,
        OBJECT,
        LIST,
        PAGE
    }

    @Builder
    public record Metadata(
            Integer current,
            Integer pageSize,
            Long totalElements,
            Integer totalPages,
            Boolean previous,
            Boolean next
    ) {
        public static Metadata ofEmpty() {
            return null;
        }

        public static Metadata ofList(List<?> data) {
            return Metadata.builder()
                    .current(0)
                    .pageSize(data.size())
                    .totalElements((long) data.size())
                    .totalPages(1)
                    .previous(false)
                    .next(false)
                    .build();
        }

        public static Metadata ofPage(Page<?> data) {
            return Metadata.builder()
                    .current(data.getNumber())
                    .pageSize(data.getSize())
                    .totalElements(data.getTotalElements())
                    .totalPages(data.getTotalPages())
                    .next(data.hasNext())
                    .previous(data.hasPrevious())
                    .build();
        }
    }

    public static <T> ApiResponse<T> list(List<T> data) {
        Metadata metadata = Metadata.ofList(data);
        return ApiResponse.<T>builder()
                .traceId(MDC.get("traceId"))
                .isSuccess(true)
                .dataType(EnumDataType.LIST.name())
                .responseAt(LocalDateTime.now())
                .metadata(metadata)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> page(Page<T> data) {
        Metadata metadata = Metadata.ofPage(data);
        List<T> content = data.getContent();
        return ApiResponse.<T>builder()
                .traceId(MDC.get("traceId"))
                .isSuccess(true)
                .dataType(EnumDataType.PAGE.name())
                .responseAt(LocalDateTime.now())
                .metadata(metadata)
                .data(content)
                .build();
    }

    public static <T> ApiResponse<T> value(T data) {
        Metadata metadata = Metadata.ofEmpty();
        return ApiResponse.<T>builder()
                .traceId(MDC.get("traceId"))
                .isSuccess(true)
                .dataType(EnumDataType.OBJECT.name())
                .responseAt(LocalDateTime.now())
                .metadata(metadata)
                .value(data)
                .build();
    }

    public static ApiResponse<String> ofApplicationException(ApplicationException applicationException) {
        Metadata metadata = Metadata.ofEmpty();
        return ApiResponse.<String>builder()
                .responseAt(LocalDateTime.now())
                .isSuccess(false)
                .errorCode(applicationException.getErrorCode().getCode())
                .messages(List.of(applicationException.getMessage()))
                .metadata(metadata)
                .traceId(MDC.get("traceId"))
                .build();
    }

    public static ApiResponse<List<String>> ofListException(List<String> errors) {
        Metadata metadata = Metadata.ofEmpty();
        return ApiResponse.<List<String>>builder()
                .traceId(MDC.get("traceId"))
                .isSuccess(false)
                .dataType(EnumDataType.EMPTY.name())
                .responseAt(LocalDateTime.now())
                .metadata(metadata)
                .messages(errors)
                .build();
    }
}
