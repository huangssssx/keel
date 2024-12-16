package com.huangssssx.keel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;  // 修改这个导入
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huangssssx.keel.model.ApiResponse;
import com.huangssssx.keel.repository.dao.SysRoleRepository;
import com.huangssssx.keel.repository.entity.SysRole;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @GetMapping("/fetchPage")
    @PreAuthorize("permitAll()")  // 使用 Spring Security 的注解替代 @PermitAll
    public ResponseEntity<?> getRoles(@RequestParam Map<String,Object> params){
        logger.info("开始获取角色列表 - 权限检查通过");
        logger.debug("请求参数: {}", params);

        try {
            // 获取分页参数 (注意：页码从0开始)
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page").toString()) - 1 : 0;
            int size = params.containsKey("size") ? Integer.parseInt(params.get("size").toString()) : 10;
            
            // 确保页码不小于0
            page = Math.max(0, page);
            
            logger.debug("处理后的分页参数 - page: {}, size: {}", page, size);
            
            // 创建分页请求
            Pageable pageable = PageRequest.of(page, size);
            
            // 获取分页数据
            Page<SysRole> pageResult = sysRoleRepository.findAll(pageable);
            
            // 添加更详细的日志
            logger.info("查询结果 - 总记录数: {}, 总页数: {}, 当前页记录数: {}, 当前页码: {}", 
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getNumberOfElements(),
                pageResult.getNumber()
            );
            
            List<SysRole> roles = pageResult.getContent();
            
            // 返回更完整的分页信息
            Map<String, Object> result = new HashMap<>();
            result.put("content", roles);
            result.put("totalElements", pageResult.getTotalElements());
            result.put("totalPages", pageResult.getTotalPages());
            result.put("currentPage", pageResult.getNumber() + 1);  // 转换回从1开始的页码
            result.put("size", pageResult.getSize());
            
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            logger.error("获取角色列表失败", e);
            throw e;
        }
    }
}
