package com.minez.iamservice.resourcemanagement.resources.domain.argument;

import com.minez.corestarter.utils.SorterUtils;
import com.minez.corestarter.utils.StringConvertUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record ResourceSearch(
        String search,
        List<String> resourceCodes,
        List<String> resourceTypes,
        List<LocalDateTime> createdAtRange,
        Boolean isEnabled,
        String sorter,
        Integer current,
        Integer pageSize
) {
    public ResourceSearch {
        search = StringConvertUtils.normalizeWhiteSpace(search);
        resourceCodes = resourceCodes != null ? resourceCodes : List.of();
        resourceTypes = resourceTypes != null ? resourceTypes : List.of();
        createdAtRange = createdAtRange != null ? createdAtRange : List.of();
    }

    public Boolean searchEmpty() {
        return !StringUtils.hasText(search);
    }

    public Boolean resourceCodesEmpty() {
        return CollectionUtils.isEmpty(resourceCodes);
    }

    public Boolean resourceTypesEmpty() {
        return CollectionUtils.isEmpty(resourceTypes);
    }

    public Boolean createdAtRangeEmpty() {
        return CollectionUtils.isEmpty(createdAtRange);
    }

    public Boolean isEnabledEmpty() {
        return isEnabled == null;
    }

    public LocalDateTime createdAtRangeBegin() {
        if(CollectionUtils.isEmpty(createdAtRange)) {
            return null;
        }
        return createdAtRange.getFirst();
    }

    public LocalDateTime createdAtRangeEnd() {
        if(createdAtRange == null || createdAtRange.isEmpty()) {
            return null;
        }
        return createdAtRange.getLast();
    }

    public PageRequest pageRequest() {
        return PageRequest.of(current, pageSize, SorterUtils.handleSorter(sorter));
    }
}
