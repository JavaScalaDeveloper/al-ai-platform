package com.al.ai.platform.core.biz.model.service;

import com.al.ai.platform.core.biz.model.dto.AiModelConfigRequest;
import com.al.ai.platform.core.biz.model.dto.AiModelConfigResponse;
import com.al.ai.platform.core.biz.model.entity.AiModelConfig;
import com.al.ai.platform.core.biz.model.mapper.AiModelConfigMapper;
import com.al.ai.platform.core.service.BaseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AiModelConfigServiceImpl
        extends BaseServiceImpl<AiModelConfigRequest, AiModelConfigResponse, AiModelConfig>
        implements AiModelConfigService {

    private final AiModelConfigMapper aiModelConfigMapper;

    @Override
    protected BaseMapper<AiModelConfig> mapper() {
        return aiModelConfigMapper;
    }

    @Override
    protected Class<AiModelConfigResponse> responseClass() {
        return AiModelConfigResponse.class;
    }

    @Override
    protected LambdaQueryWrapper<AiModelConfig> buildWrapper(AiModelConfigRequest request) {
        return new LambdaQueryWrapper<AiModelConfig>()
                .like(StringUtils.hasText(request.getModelCode()), AiModelConfig::getModelCode, request.getModelCode())
                .like(StringUtils.hasText(request.getModelName()), AiModelConfig::getModelName, request.getModelName())
                .eq(StringUtils.hasText(request.getProvider()), AiModelConfig::getProvider, request.getProvider())
                .eq(request.getStatus() != null, AiModelConfig::getStatus, request.getStatus())
                .orderByDesc(AiModelConfig::getId);
    }

    @Override
    protected AiModelConfigResponse toResponse(AiModelConfig entity) {
        AiModelConfigResponse res = super.toResponse(entity);
        if (res != null && StringUtils.hasText(entity.getApiKey())) {
            String key = entity.getApiKey();
            res.setApiKeyMasked(key.length() <= 8 ? "****" : key.substring(0, 4) + "****" + key.substring(key.length() - 4));
        }
        return res;
    }
}
