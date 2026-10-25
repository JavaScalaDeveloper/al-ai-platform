package com.al.ai.platform.core.biz.knowledge.service;

import com.al.ai.platform.core.biz.knowledge.dto.AiKnowledgeBaseRequest;
import com.al.ai.platform.core.biz.knowledge.dto.AiKnowledgeBaseResponse;
import com.al.ai.platform.core.biz.knowledge.entity.AiKnowledgeBase;
import com.al.ai.platform.core.biz.knowledge.mapper.AiKnowledgeBaseMapper;
import com.al.ai.platform.core.service.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AiKnowledgeBaseServiceImpl
        extends BaseServiceImpl<AiKnowledgeBaseRequest, AiKnowledgeBaseResponse, AiKnowledgeBase>
        implements AiKnowledgeBaseService {

    private final AiKnowledgeBaseMapper aiKnowledgeBaseMapper;

    @Override
    protected BaseMapper<AiKnowledgeBase> mapper() {
        return aiKnowledgeBaseMapper;
    }

    @Override
    protected Class<AiKnowledgeBaseResponse> responseClass() {
        return AiKnowledgeBaseResponse.class;
    }

    @Override
    protected LambdaQueryWrapper<AiKnowledgeBase> buildWrapper(AiKnowledgeBaseRequest request) {
        return new LambdaQueryWrapper<AiKnowledgeBase>()
                .like(StringUtils.hasText(request.getKbCode()), AiKnowledgeBase::getKbCode, request.getKbCode())
                .like(StringUtils.hasText(request.getKbName()), AiKnowledgeBase::getKbName, request.getKbName())
                .eq(request.getStatus() != null, AiKnowledgeBase::getStatus, request.getStatus())
                .orderByDesc(AiKnowledgeBase::getId);
    }
}
