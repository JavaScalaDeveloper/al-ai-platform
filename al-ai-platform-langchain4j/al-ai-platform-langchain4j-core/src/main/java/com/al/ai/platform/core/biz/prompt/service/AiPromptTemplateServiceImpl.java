package com.al.ai.platform.core.biz.prompt.service;

import com.al.ai.platform.core.biz.prompt.dto.AiPromptTemplateRequest;
import com.al.ai.platform.core.biz.prompt.dto.AiPromptTemplateResponse;
import com.al.ai.platform.core.biz.prompt.entity.AiPromptTemplate;
import com.al.ai.platform.core.biz.prompt.mapper.AiPromptTemplateMapper;
import com.al.ai.platform.core.service.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AiPromptTemplateServiceImpl
        extends BaseServiceImpl<AiPromptTemplateRequest, AiPromptTemplateResponse, AiPromptTemplate>
        implements AiPromptTemplateService {

    private final AiPromptTemplateMapper aiPromptTemplateMapper;

    @Override
    protected BaseMapper<AiPromptTemplate> mapper() {
        return aiPromptTemplateMapper;
    }

    @Override
    protected Class<AiPromptTemplateResponse> responseClass() {
        return AiPromptTemplateResponse.class;
    }

    @Override
    protected LambdaQueryWrapper<AiPromptTemplate> buildWrapper(AiPromptTemplateRequest request) {
        return new LambdaQueryWrapper<AiPromptTemplate>()
                .like(StringUtils.hasText(request.getPromptCode()), AiPromptTemplate::getPromptCode, request.getPromptCode())
                .like(StringUtils.hasText(request.getPromptName()), AiPromptTemplate::getPromptName, request.getPromptName())
                .eq(StringUtils.hasText(request.getCategory()), AiPromptTemplate::getCategory, request.getCategory())
                .eq(request.getStatus() != null, AiPromptTemplate::getStatus, request.getStatus())
                .orderByDesc(AiPromptTemplate::getId);
    }
}
