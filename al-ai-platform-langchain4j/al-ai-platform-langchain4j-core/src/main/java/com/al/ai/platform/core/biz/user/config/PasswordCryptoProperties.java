package com.al.ai.platform.core.biz.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录密码传输加密（RSA）：前端公钥加密，后端私钥解密。
 */
@Data
@Component
@ConfigurationProperties(prefix = "alai.crypto.rsa")
public class PasswordCryptoProperties {
    /** Base64(X.509 SubjectPublicKeyInfo) */
    private String publicKey;
    /** Base64(PKCS#8 PrivateKey) */
    private String privateKey;
}
