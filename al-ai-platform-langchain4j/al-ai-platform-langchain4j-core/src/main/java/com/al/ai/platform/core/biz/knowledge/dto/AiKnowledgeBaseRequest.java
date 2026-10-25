package com.al.ai.platform.core.biz.knowledge.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiKnowledgeBaseRequest extends AiKnowledgeBaseResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
