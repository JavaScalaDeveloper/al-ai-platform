package com.al.ai.platform.core.biz.chat.service;

import com.al.ai.platform.core.biz.chat.dto.AiChatMessageRequest;
import com.al.ai.platform.core.biz.chat.dto.AiChatMessageResponse;
import com.al.ai.platform.core.biz.chat.entity.AiChatMessage;
import com.al.ai.platform.core.biz.chat.mapper.AiChatMessageMapper;
import com.al.ai.platform.core.service.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiChatMessageServiceImpl
        extends BaseServiceImpl<AiChatMessageRequest, AiChatMessageResponse, AiChatMessage>
        implements AiChatMessageService {

    private final AiChatMessageMapper aiChatMessageMapper;

    @Override
    protected BaseMapper<AiChatMessage> mapper() {
        return aiChatMessageMapper;
    }

    @Override
    protected Class<AiChatMessageResponse> responseClass() {
        return AiChatMessageResponse.class;
    }

    @Override
    protected LambdaQueryWrapper<AiChatMessage> buildWrapper(AiChatMessageRequest request) {
        return new LambdaQueryWrapper<AiChatMessage>()
                .eq(request.getSessionId() != null, AiChatMessage::getSessionId, request.getSessionId())
                .eq(request.getStatus() != null, AiChatMessage::getStatus, request.getStatus())
                .orderByAsc(AiChatMessage::getId);
    }
}
