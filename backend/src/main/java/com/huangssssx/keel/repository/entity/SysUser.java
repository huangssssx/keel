package com.huangssssx.keel.repository.entity;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    private String nickname;

    private String email;

    private String phone;

    @Column(name="status",nullable = false)
    private Boolean status;

    @Column(name="create_time",nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name="last_login_time")
    private LocalDateTime lastLoginTime;
}
