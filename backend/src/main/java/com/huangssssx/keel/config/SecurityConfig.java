package com.huangssssx.keel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.huangssssx.keel.repository.dao.SysPermissionRepository;
import com.huangssssx.keel.repository.dao.SysUserRepository;
import com.huangssssx.keel.repository.entity.SysPermission;
import com.huangssssx.keel.repository.entity.SysUser;
import com.huangssssx.keel.security.JwtAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/authenticate").permitAll()
                .requestMatchers("/api/auth/getUserInfo").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )            
            // 添加JWT过滤器（用于判断每个请求的token是否有效）
            .addFilterBefore(jwtAuthenticationFilter, 
                    UsernamePasswordAuthenticationFilter.class);;
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            SysUser user = sysUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
            
            // 获取用户的角色和权限
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            
            // 添加用户角色和对应的权限
            user.getRoles().stream()
                .filter(role -> role.getStatus())
                .forEach(role -> {
                    // 添加角色
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
                    
                    // 添加该角色的所有权限
                    role.getPermissions().stream()
                        .filter(permission -> permission.getStatus())
                        .forEach(permission -> {
                            authorities.add(new SimpleGrantedAuthority(permission.getPermissionCode()));
                        });
                });
            
            return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.getStatus())
                .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return new ProviderManager(authProvider);
    }

//        // 可以直接在这里添加一个测试方法
//        @PostConstruct
//        public void testEncoder() {
//            String rawPassword = "huang12563";
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            String encodedPassword = encoder.encode(rawPassword);
//            System.out.println("原始密码: " + rawPassword);
//            System.out.println("加密后的密码: " + encodedPassword);
//
//            // 验证密码
//            boolean matches = encoder.matches(rawPassword, encodedPassword);
//            System.out.println("密码匹配: " + matches);
//        }
}