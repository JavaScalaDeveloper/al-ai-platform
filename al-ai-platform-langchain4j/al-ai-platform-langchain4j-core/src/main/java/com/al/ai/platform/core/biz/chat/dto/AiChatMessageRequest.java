package com.al.ai.platform.core.biz.chat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiChatMessageRequest extends AiChatMessageResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 50;
}
