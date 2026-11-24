package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.corestarter.utils.StringConvertUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.util.StringUtils;

class ResourceEntityListener {
    @PrePersist
    public void prePersist(ResourceJpaEntity entity) {
        Boolean isEnabled = entity.getIsEnabled();
        String resourceName = StringConvertUtils.normalizeWhiteSpace(entity.getResourceName());
        String resourceCode =  StringConvertUtils.normalizeWhiteSpace(entity.getResourceCode());
        String computeSearch = "%s%s".formatted(resourceName, resourceCode);
        entity.setSearch(computeSearch);
        if(isEnabled == null) {
            Boolean computeIsEnabled = true;
            entity.setIsEnabled(computeIsEnabled);
        }
    }

    @PreUpdate
    public void preUpdate(ResourceJpaEntity entity) {
        String resourceName = StringConvertUtils.normalizeWhiteSpace(entity.getResourceName());
        String resourceCode =  StringConvertUtils.normalizeWhiteSpace(entity.getResourceCode());
        String computeSearch = "%s%s".formatted(resourceName, resourceCode);
        entity.setSearch(computeSearch);
    }
}
