package com.huangssssx.keel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.huangssssx.keel.model.LoginRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        logger.info("收到登录请求: {}", loginRequest.getUsername());
        // TODO: 实现实际的认证逻辑
        return ResponseEntity.ok().body(Map.of("token", "dummy-token"));
    }
} 