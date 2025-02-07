package com.huangssssx.keel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable; // 修改这个导入
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.huangssssx.keel.model.ApiResponse;
import com.huangssssx.keel.repository.dao.SysUserRepository;
import com.huangssssx.keel.repository.entity.SysRole;
import com.huangssssx.keel.repository.entity.SysUser; // 添加这行导入

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SysUserRepository sysUserRepository;

    @GetMapping("/fetchUserPage")
    @PreAuthorize("permitAll()") // 使用 Spring Security 的注解替代 @PermitAll
    public ResponseEntity<?> list(@RequestParam Map<String, Object> params) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page").toString()) - 1 : 0;
        int size = params.containsKey("size") ? Integer.parseInt(params.get("size").toString()) : 10;
        page = Math.max(0, page);
        Pageable pageable = PageRequest.of(page, size);
        String username = params.getOrDefault("username", "").toString();
        Page<SysUser> pageResult = sysUserRepository.findByUsernameContaining(username, pageable);

        List<SysUser> users = pageResult.getContent();
        // 返回更完整的分页信息
        Map<String, Object> result = new HashMap<>();
        result.put("content", users);
        result.put("totalElements", pageResult.getTotalElements());
        result.put("totalPages", pageResult.getTotalPages());
        result.put("currentPage", pageResult.getNumber() + 1); // 转换回从1开始的页码
        result.put("size", pageResult.getSize());

        System.out.println(params);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}