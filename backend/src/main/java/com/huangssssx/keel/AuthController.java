package com.huangssssx.keel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public Map<String, String> authenticate(@RequestBody AuthRequest authRequest) {
        // 使用硬编码的用户凭据进行验证
        if ("user".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getUsername());
            System.out.println("############################Token: " + token);
            return Collections.singletonMap("token", token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
