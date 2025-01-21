package com.huangssssx.keel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController{


    @GetMapping("/list")
    @PreAuthorize("permitAll()")  // 使用 Spring Security 的注解替代 @PermitAll
    public ResponseEntity<?> list(@RequestParam Map<String,Object> params){
         System.out.println(params);
        return null;
    }

}