CREATE TABLE scopes (
    scope_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    scope_uuid CHAR(36)  UNIQUE NOT NULL,
    scope_code VARCHAR(100) UNIQUE NOT NULL,
    scope_name VARCHAR(256) NOT NULL,
    is_enabled BOOL DEFAULT TRUE,
    search VARCHAR(4000) NOT NULL,
    created_by VARCHAR(526) DEFAULT NULL,
    created_at DATETIME DEFAULT NULL,
    updated_by VARCHAR(526) DEFAULT NULL,
    updated_at DATETIME DEFAULT NULL
);

CREATE INDEX idx_scope_uuid ON scopes(scope_uuid);
CREATE INDEX idx_scope_code ON scopes(scope_code);