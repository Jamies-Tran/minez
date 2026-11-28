package com.minez.iamservice.resourcemanagement.resources.repository;

import com.minez.corestarter.auditor.BaseAuditorEntity;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Resource")
@EntityListeners(ResourceEntityListener.class)
@Table(name = "resources")
@FieldDefaults(level = AccessLevel.PRIVATE)
class ResourceJpaEntity extends BaseAuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long resourceId;

    @Column(name = "parent_uuid")
    @JdbcTypeCode(Types.CHAR)
    UUID parentUuid;

    @Column(name = "resource_uuid", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID resourceUuid;

    @Column(name = "resource_code", unique = true, nullable = false)
    String resourceCode;

    @Column(name = "resource_name", nullable = false)
    String resourceName;

    @Column(name = "resource_type", nullable = false)
    String resourceType;

    @Column(name = "description")
    String description;

    @Column(name = "is_enabled", nullable = false)
    Boolean isEnabled;

    @Column(name = "search", nullable = false)
    String search;

    public void applyDomain(Resource resource) {
        this.setParentUuid(resource.parentUuid());
        this.setResourceCode(resource.resourceCode());
        this.setResourceName(resource.resourceName());
        this.setResourceType(resource.resourceType());
        this.setDescription(resource.description());
        Boolean isEnabled = resource.isEnabled();
        if(Objects.nonNull(isEnabled)) {
            this.setIsEnabled(isEnabled);
        }

    }

    public void setEnable() {
        this.setIsEnabled(true);
    }

    public void setDisable() {
        this.setIsEnabled(false);
    }

    public Resource convertDomain() {
        return Resource.builder()
                .resourceId(this.getResourceId())
                .parentUuid(this.getParentUuid())
                .resourceUuid(this.getResourceUuid())
                .resourceCode(this.getResourceCode())
                .resourceName(this.getResourceName())
                .resourceType(this.getResourceType())
                .description(this.getDescription())
                .isEnabled(this.getIsEnabled())
                .createdBy(this.getCreatedBy())
                .createdAt(this.getCreatedAt())
                .updatedBy(this.getUpdatedBy())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}
