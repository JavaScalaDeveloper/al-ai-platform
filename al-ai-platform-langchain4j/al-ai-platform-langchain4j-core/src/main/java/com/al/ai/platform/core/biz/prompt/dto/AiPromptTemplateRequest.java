package com.al.ai.platform.core.biz.prompt.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiPromptTemplateRequest extends AiPromptTemplateResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
