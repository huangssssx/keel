package com.huangssssx.keel.repository.dao;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huangssssx.keel.repository.entity.SysPermission;

@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long> {
    @Query("SELECT p FROM SysPermission p JOIN p.roles r WHERE r.id = :roleId AND p.status = :status")
    Set<SysPermission> findByRoleIdAndStatus(@Param("roleId") Long roleId, @Param("status") Boolean status);
} 