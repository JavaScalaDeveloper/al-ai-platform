package com.al.ai.platform.core.biz.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsaPublicKeyResponse {
    /** PEM 格式公钥，供前端 JSEncrypt 使用 */
    private String publicKeyPem;
}
