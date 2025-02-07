package com.huangssssx.keel.repository.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.huangssssx.keel.repository.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    Optional<SysUser> findByUsername(String username);

    Page<SysUser> findByUsernameContaining(String username, Pageable pageable);
}
