package com.al.ai.platform.admin.controller;

import com.al.ai.platform.admin.dto.TokenRequest;
import com.al.ai.platform.core.biz.user.dto.AdminLoginRequest;
import com.al.ai.platform.core.biz.user.dto.AdminLoginResponse;
import com.al.ai.platform.core.biz.user.dto.RsaPublicKeyResponse;
import com.al.ai.platform.core.biz.user.service.AdminAuthService;
import com.al.ai.platform.core.biz.user.service.PasswordCryptoService;
import com.al.ai.platform.core.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;
    private final PasswordCryptoService passwordCryptoService;

    /** 获取登录密码加密用 RSA 公钥（PEM） */
    @PostMapping("/crypto/public-key")
    public Result<RsaPublicKeyResponse> publicKey() {
        return Result.success(new RsaPublicKeyResponse(passwordCryptoService.publicKeyPem()));
    }

    @PostMapping("/login")
    public Result<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return Result.success(adminAuthService.login(request));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestBody(required = false) TokenRequest body,
                               @RequestHeader(value = "Authorization", required = false) String authorization) {
        String token = body != null ? body.getToken() : null;
        if (!StringUtils.hasText(token) && StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }
        adminAuthService.logout(token);
        return Result.success();
    }

    @PostMapping("/profile")
    public Result<AdminLoginResponse> profile(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            return Result.error(401, "未登录");
        }
        return Result.success(adminAuthService.validateToken(authorization.substring(7)));
    }

    @PostMapping("/dashboard")
    public Result<java.util.Map<String, Object>> dashboard() {
        return Result.success(java.util.Map.of(
                "product", "AL AI Platform",
                "modules", java.util.List.of("chat", "knowledge", "model", "ops"),
                "status", "ready"
        ));
    }
}
