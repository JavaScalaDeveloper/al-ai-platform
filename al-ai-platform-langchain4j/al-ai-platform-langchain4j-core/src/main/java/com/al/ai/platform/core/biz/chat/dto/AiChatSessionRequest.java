package com.al.ai.platform.core.biz.chat.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AiChatSessionRequest extends AiChatSessionResponse {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
