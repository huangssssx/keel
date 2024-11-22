package com.huangssssx.keel.controller;

import com.huangssssx.keel.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.huangssssx.keel.model.LoginRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        logger.info("收到登录请求: {}", loginRequest.getUsername());
        
        try {
            logger.debug("尝试认证用户: {}", loginRequest.getUsername());
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            
            logger.debug("用户认证成功: {}", authentication.getName());
            
            final String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            logger.debug("JWT令牌生成成功");
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", loginRequest.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            logger.error("认证失败: 用户名或密码错误", e);
            return ResponseEntity.status(401).body(Map.of("message", "用户名或密码错误"));
        } catch (Exception e) {
            logger.error("认证过程发生错误: ", e);
            return ResponseEntity.status(500).body(Map.of(
                "message", "认证过程发生错误",
                "error", e.getMessage()
            ));
        }
    }
} 