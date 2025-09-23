package com.wserp.organizationservice.entity;


import com.wserp.common.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "erp_organizations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String ownerId;
    @Column(name = "description")
    private String description;
    @Column(name = "logo")
    private String logo;

}
