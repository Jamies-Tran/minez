CREATE TABLE resources (
    resource_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_uuid CHAR(36) DEFAULT NULL,
    resource_uuid CHAR(36) UNIQUE NOT NULL,
    resource_code VARCHAR(100) UNIQUE NOT NULL,
    resource_name VARCHAR(256) NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    description VARCHAR(4000) DEFAULT NULL,
    is_enabled BOOL DEFAULT TRUE,
    search VARCHAR(4000) NOT NULL,
    created_by VARCHAR(526) DEFAULT NULL,
    created_at DATETIME DEFAULT NULL,
    updated_by VARCHAR(526) DEFAULT NULL,
    updated_at DATETIME DEFAULT NULL
);

CREATE INDEX idx_parent_uuid ON resources(parent_uuid);
CREATE INDEX idx_resource_uuid ON resources(resource_uuid);
CREATE INDEX idx_resource_code ON resources(resource_code);