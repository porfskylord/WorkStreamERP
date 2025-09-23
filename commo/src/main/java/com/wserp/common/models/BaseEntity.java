package com.wserp.common.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity implements Serializable {

    @Serial
    protected static long serialVersionUID = 1L;

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    protected boolean isDeleted;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 36)
    protected String deletedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false, length = 36, updatable = false)
    protected String createdBy;

    @Column(name = "updated_by", nullable = false, length = 36)
    protected String updatedBy;

    @PreUpdate
    @PreRemove
    private void beforeDelete() {
        if (isDeleted && deletedAt == null) {
            deletedAt = LocalDateTime.now();
        }
    }
}