-- 使用BCrypt加密的密码 'huang12563'
INSERT INTO sys_user (id, username, password, status, created_at, updated_at)
VALUES (1, 'admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', true, NOW(), NOW())
ON DUPLICATE KEY UPDATE password = VALUES(password);

-- 初始化角色
INSERT INTO sys_role (id, role_name, role_code, status, created_at, updated_at)
VALUES (1, '管理员', 'ADMIN', true, NOW(), NOW())
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

-- 关联用户和角色
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);