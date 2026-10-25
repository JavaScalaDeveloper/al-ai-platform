package com.al.ai.platform.core.biz.chat.service;

import com.al.ai.platform.core.biz.chat.dto.AiChatSessionRequest;
import com.al.ai.platform.core.biz.chat.dto.AiChatSessionResponse;
import com.al.ai.platform.core.biz.chat.entity.AiChatSession;
import com.al.ai.platform.core.biz.chat.mapper.AiChatSessionMapper;
import com.al.ai.platform.core.service.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AiChatSessionServiceImpl
        extends BaseServiceImpl<AiChatSessionRequest, AiChatSessionResponse, AiChatSession>
        implements AiChatSessionService {

    private final AiChatSessionMapper aiChatSessionMapper;

    @Override
    protected BaseMapper<AiChatSession> mapper() {
        return aiChatSessionMapper;
    }

    @Override
    protected Class<AiChatSessionResponse> responseClass() {
        return AiChatSessionResponse.class;
    }

    @Override
    protected LambdaQueryWrapper<AiChatSession> buildWrapper(AiChatSessionRequest request) {
        return new LambdaQueryWrapper<AiChatSession>()
                .eq(StringUtils.hasText(request.getSessionCode()), AiChatSession::getSessionCode, request.getSessionCode())
                .like(StringUtils.hasText(request.getTitle()), AiChatSession::getTitle, request.getTitle())
                .eq(request.getStatus() != null, AiChatSession::getStatus, request.getStatus())
                .orderByDesc(AiChatSession::getId);
    }
}
