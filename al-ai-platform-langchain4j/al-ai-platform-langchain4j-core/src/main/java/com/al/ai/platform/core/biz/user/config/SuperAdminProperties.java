package com.al.ai.platform.core.biz.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 管理端超管账号（写在 yml，不依赖数据库）。
 */
@Data
@Component
@ConfigurationProperties(prefix = "alai.admin.super")
public class SuperAdminProperties {
    /** 超管用户名 */
    private String username = "superadmin";
    /** 超管密码（明文，生产请用环境变量覆盖） */
    private String password = "SuperAdmin@8282";
    /** 展示昵称 */
    private String nickname = "超级管理员";
}
