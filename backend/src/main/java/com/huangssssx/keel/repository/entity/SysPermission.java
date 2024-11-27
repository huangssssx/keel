package com.huangssssx.keel.repository.entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.ManyToMany;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_permission")
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(name = "permission_name", nullable = false)
    private String permissionName;
    
    @Column(name = "permission_code", nullable = false)
    private String permissionCode;
    
    @Column(name = "permission_type", nullable = false)
    private Integer permissionType;
    
    private String path;
    
    private String component;
    
    private String icon;
    
    private Integer sort;
    
    private Boolean status;
    
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SysRole> roles = new HashSet<>();
} 