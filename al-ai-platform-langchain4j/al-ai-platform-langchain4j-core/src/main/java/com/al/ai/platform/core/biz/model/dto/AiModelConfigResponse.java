package com.al.ai.platform.core.biz.model.dto;

import com.al.ai.platform.core.biz.model.entity.AiModelConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiModelConfigResponse extends AiModelConfig {
    /** 前端展示用，脱敏后的 key 提示 */
    private String apiKeyMasked;

    @JsonIgnore
    @Override
    public String getApiKey() {
        return super.getApiKey();
    }
}
