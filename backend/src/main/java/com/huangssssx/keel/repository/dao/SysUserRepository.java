package com.huangssssx.keel.repository.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huangssssx.keel.repository.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    
}
