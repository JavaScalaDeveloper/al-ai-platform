package com.al.ai.platform.core.biz.user.service;

import cn.hutool.core.util.IdUtil;
import com.al.ai.platform.core.biz.user.config.SuperAdminProperties;
import com.al.ai.platform.core.biz.user.dto.AdminLoginRequest;
import com.al.ai.platform.core.biz.user.dto.AdminLoginResponse;
import com.al.ai.platform.core.biz.user.entity.AdminAccount;
import com.al.ai.platform.core.biz.user.entity.UserAuthSession;
import com.al.ai.platform.core.biz.user.mapper.AdminAccountMapper;
import com.al.ai.platform.core.biz.user.mapper.UserAuthSessionMapper;
import com.al.ai.platform.core.common.exception.BusinessException;
import com.al.ai.platform.core.common.result.ResultCode;
import com.al.ai.platform.core.common.time.ChinaTime;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {

    private static final int TOKEN_HOURS = 24;
    /** yml 超管在会话里的虚拟用户 ID */
    private static final long SUPER_ADMIN_USER_ID = 0L;

    private final AdminAccountMapper adminAccountMapper;
    private final UserAuthSessionMapper userAuthSessionMapper;
    private final SuperAdminProperties superAdminProperties;
    private final PasswordCryptoService passwordCryptoService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        if (request == null || !StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword())) {
            throw new BusinessException(ResultCode.PARAMS_VALIDATION_ERROR, "用户名和密码不能为空");
        }

        // 前端 RSA 加密传输，后端先解密再校验
        String plainPassword = passwordCryptoService.decryptPassword(request.getPassword());

        // 1) yml 超管优先
        if (isSuperAdmin(request.getUsername(), plainPassword)) {
            return issueSession(
                    SUPER_ADMIN_USER_ID,
                    superAdminProperties.getUsername(),
                    StringUtils.hasText(superAdminProperties.getNickname())
                            ? superAdminProperties.getNickname()
                            : "超级管理员",
                    null,
                    "SUPER_ADMIN"
            );
        }

        // 2) 数据库管理员
        AdminAccount account = adminAccountMapper.selectOne(new LambdaQueryWrapper<AdminAccount>()
                .eq(AdminAccount::getUsername, request.getUsername())
                .last("limit 1"));
        if (account == null) {
            throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
        }
        if (account.getStatus() != null && account.getStatus() == 0) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_DISABLED);
        }
        if (!passwordEncoder.matches(plainPassword, account.getPassword())) {
            if (!(account.getPassword() != null && account.getPassword().equals(plainPassword))) {
                throw new BusinessException(ResultCode.USER_PASSWORD_ERROR);
            }
        }
        return issueSession(
                account.getId(),
                account.getUsername(),
                account.getNickname(),
                account.getAvatar(),
                "ADMIN"
        );
    }

    @Override
    public void logout(String token) {
        if (!StringUtils.hasText(token)) {
            return;
        }
        userAuthSessionMapper.delete(new LambdaQueryWrapper<UserAuthSession>().eq(UserAuthSession::getToken, token));
    }

    @Override
    public AdminLoginResponse validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ResultCode.USER_NOT_LOGIN);
        }
        UserAuthSession session = userAuthSessionMapper.selectOne(new LambdaQueryWrapper<UserAuthSession>()
                .eq(UserAuthSession::getToken, token)
                .last("limit 1"));
        if (session == null) {
            throw new BusinessException(ResultCode.USER_TOKEN_INVALID);
        }
        if (session.getExpireTime() != null && session.getExpireTime().isBefore(ChinaTime.now())) {
            userAuthSessionMapper.deleteById(session.getId());
            throw new BusinessException(ResultCode.USER_TOKEN_EXPIRED);
        }
        Map<String, Object> info = JSON.parseObject(session.getUserInfo());
        return new AdminLoginResponse(
                token,
                info.get("id") == null ? null : Long.valueOf(String.valueOf(info.get("id"))),
                info.get("username") == null ? null : String.valueOf(info.get("username")),
                info.get("nickname") == null ? null : String.valueOf(info.get("nickname"))
        );
    }

    private boolean isSuperAdmin(String username, String password) {
        return StringUtils.hasText(superAdminProperties.getUsername())
                && StringUtils.hasText(superAdminProperties.getPassword())
                && superAdminProperties.getUsername().equals(username)
                && superAdminProperties.getPassword().equals(password);
    }

    private AdminLoginResponse issueSession(Long userId, String username, String nickname, String avatar, String userType) {
        String token = IdUtil.fastSimpleUUID();
        LocalDateTime expire = ChinaTime.now().plusHours(TOKEN_HOURS);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", userId);
        userInfo.put("username", username);
        userInfo.put("nickname", nickname);
        userInfo.put("avatar", avatar);
        userInfo.put("userType", userType);

        UserAuthSession session = new UserAuthSession();
        session.setToken(token);
        session.setExpireTime(expire);
        session.setUserType(userType);
        session.setUserInfo(JSON.toJSONString(userInfo));
        userAuthSessionMapper.insert(session);

        return new AdminLoginResponse(token, userId, username, nickname);
    }
}
