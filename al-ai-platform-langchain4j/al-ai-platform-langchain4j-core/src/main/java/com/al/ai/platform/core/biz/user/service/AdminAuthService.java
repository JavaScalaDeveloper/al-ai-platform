package com.al.ai.platform.core.biz.user.service;

import com.al.ai.platform.core.biz.user.dto.AdminLoginRequest;
import com.al.ai.platform.core.biz.user.dto.AdminLoginResponse;

public interface AdminAuthService {
    AdminLoginResponse login(AdminLoginRequest request);
    void logout(String token);
    AdminLoginResponse validateToken(String token);
}
