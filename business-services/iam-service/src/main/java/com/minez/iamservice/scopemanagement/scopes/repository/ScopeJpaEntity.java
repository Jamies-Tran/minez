package com.minez.iamservice.scopemanagement.scopes.repository;

import com.minez.corestarter.auditor.BaseAuditorEntity;
import com.minez.iamservice.scopemanagement.scopes.domain.Scope;
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
@Entity(name = "Scope")
@EntityListeners(ScopeEntityListener.class)
@Table(name = "scopes")
@FieldDefaults(level = AccessLevel.PRIVATE)
class ScopeJpaEntity extends BaseAuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long scopeId;

    @Column(name = "scope_uuid", unique = true, nullable = false, updatable = false)
    @JdbcTypeCode(Types.CHAR)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID scopeUuid;

    @Column(name = "scope_code", unique = true, nullable = false)
    String scopeCode;

    @Column(name = "scope_name", nullable = false)
    String scopeName;

    @Column(name = "is_enabled", nullable = false)
    Boolean isEnabled;

    @Column(name = "search", nullable = false)
    String search;

    public void applyDomain(Scope scope) {
        this.setScopeCode(scope.scopeCode());
        this.setScopeName(scope.scopeName());
        Boolean isEnabled = scope.isEnabled();
        if(Objects.nonNull(isEnabled)) {
            this.setIsEnabled(isEnabled);
        }
    }

    public Scope convertDomain() {
        return Scope.builder()
                .scopeId(this.getScopeId())
                .scopeUuid(this.getScopeUuid())
                .scopeCode(this.getScopeCode())
                .scopeName(this.getScopeName())
                .isEnabled(this.getIsEnabled())
                .createdBy(this.getCreatedBy())
                .createdAt(this.getCreatedAt())
                .updatedBy(this.getUpdatedBy())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public void enable() {
        Boolean isEnabled = true;
        this.setIsEnabled(isEnabled);
    }

    public void disable() {
        Boolean isEnabled = false;
        this.setIsEnabled(isEnabled);
    }
}
