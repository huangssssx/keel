package com.huangssssx.keel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/protected")
    public String protectedEndpoint(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token);
        if (jwtUtil.validateToken(token, username)) {
            return "This is a protected endpoint. Hello, " + username + "!";
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}
