package com.minez.corestarter.template;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record Result<T>(
        T data
) {
    public static <T> Result<T> value(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<Page<T>> page(Page<T> data) {
        return new Result<Page<T>>(data);
    }

    public static <T> Result<List<T>> page(List<T> data) {
        return new Result<List<T>>(data);
    }
}
