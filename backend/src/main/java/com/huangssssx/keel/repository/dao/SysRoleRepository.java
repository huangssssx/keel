package com.huangssssx.keel.repository.dao;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huangssssx.keel.repository.entity.SysRole;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
    // 不需要 findByUserIdAndStatus 方法，因为我们可以通过 SysUser 的 roles 属性获取角色
    @Query("SELECT COUNT(r) FROM SysRole r")
    long countRoles();
} 