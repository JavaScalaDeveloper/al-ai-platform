package com.al.ai.platform.core.biz.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiModelConfigRequest extends AiModelConfigResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
