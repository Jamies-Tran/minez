package com.minez.iamservice.scopemanagement.scopes.domain.argument;

import com.minez.corestarter.utils.SorterUtils;
import com.minez.corestarter.utils.StringConvertUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record ScopeSearch(
        String search,
        List<String> scopeCodes,
        List<LocalDateTime> createdAtRange,
        Boolean isEnabled,
        String sorter,
        Integer current,
        Integer pageSize
) {
    public ScopeSearch {
        search = StringConvertUtils.normalizeWhiteSpace(search);
        scopeCodes = Objects.isNull(scopeCodes) ? List.of() : scopeCodes;
        createdAtRange = Objects.isNull(createdAtRange) ? List.of() : createdAtRange;
    }

    public Boolean searchEmpty() {
        return !StringUtils.hasText(search);
    }

    public Boolean scopeCodesEmpty() {
        return CollectionUtils.isEmpty(scopeCodes);
    }

    public Boolean createdAtRangeEmpty() {
        return CollectionUtils.isEmpty(createdAtRange);
    }

    public Boolean isEnabledEmpty() {
        return Objects.isNull(isEnabled);
    }

    public LocalDateTime createdAtRangeBegin() {
        if(CollectionUtils.isEmpty(createdAtRange)) {
            return null;
        }
        return createdAtRange.getFirst();
    }

    public LocalDateTime createdAtRangeEnd() {
        if(CollectionUtils.isEmpty(createdAtRange)) {
            return null;
        }
        return createdAtRange.getLast();
    }

    public PageRequest pageRequest() {
        return PageRequest.of(current, pageSize, SorterUtils.handleSorter(sorter));
    }
}
