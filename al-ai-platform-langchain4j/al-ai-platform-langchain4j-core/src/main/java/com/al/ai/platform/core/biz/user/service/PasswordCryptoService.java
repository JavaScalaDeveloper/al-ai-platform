package com.al.ai.platform.core.biz.user.service;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.al.ai.platform.core.biz.user.config.PasswordCryptoProperties;
import com.al.ai.platform.core.common.exception.BusinessException;
import com.al.ai.platform.core.common.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PasswordCryptoService {

    private final PasswordCryptoProperties properties;

    public String publicKeyPem() {
        requireConfigured();
        String b64 = properties.getPublicKey().replaceAll("\\s+", "");
        return wrapPem("PUBLIC KEY", b64);
    }

    public String publicKeyBase64() {
        requireConfigured();
        return properties.getPublicKey().replaceAll("\\s+", "");
    }

    /**
     * 解密前端传来的 RSA 密文（Base64），得到明文密码。
     */
    public String decryptPassword(String cipherBase64) {
        requireConfigured();
        if (!StringUtils.hasText(cipherBase64)) {
            throw new BusinessException(ResultCode.PARAMS_VALIDATION_ERROR, "密码不能为空");
        }
        try {
            RSA rsa = new RSA(properties.getPrivateKey().replaceAll("\\s+", ""),
                    properties.getPublicKey().replaceAll("\\s+", ""));
            String plain = rsa.decryptStr(cipherBase64.trim(), KeyType.PrivateKey);
            if (!StringUtils.hasText(plain)) {
                throw new BusinessException(ResultCode.PARAMS_VALIDATION_ERROR, "密码解密失败");
            }
            return plain;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResultCode.PARAMS_VALIDATION_ERROR, "密码须经前端加密后传输");
        }
    }

    private void requireConfigured() {
        if (!StringUtils.hasText(properties.getPublicKey()) || !StringUtils.hasText(properties.getPrivateKey())) {
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "RSA 密钥未配置");
        }
    }

    private static String wrapPem(String type, String base64) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN ").append(type).append("-----\n");
        for (int i = 0; i < base64.length(); i += 64) {
            sb.append(base64, i, Math.min(i + 64, base64.length())).append('\n');
        }
        sb.append("-----END ").append(type).append("-----");
        return sb.toString();
    }
}
