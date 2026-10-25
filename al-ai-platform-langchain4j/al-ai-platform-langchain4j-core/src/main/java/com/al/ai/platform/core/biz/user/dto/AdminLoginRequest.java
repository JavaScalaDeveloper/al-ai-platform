package com.al.ai.platform.core.biz.user.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String username;
    /** RSA 公钥加密后的 Base64 密文，禁止传明文 */
    private String password;
}
