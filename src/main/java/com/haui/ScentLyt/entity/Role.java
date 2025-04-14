package com.haui.ScentLyt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @ColumnDefault("''")
    @Column(name = "role_name", length = 45)
    private String roleName;

    @ColumnDefault("1")
    @Column(name = "active")
    private Boolean active;

}