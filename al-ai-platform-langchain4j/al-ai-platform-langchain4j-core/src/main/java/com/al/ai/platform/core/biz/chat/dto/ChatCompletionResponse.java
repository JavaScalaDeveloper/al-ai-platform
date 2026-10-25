package com.al.ai.platform.core.biz.chat.dto;

import lombok.Data;

@Data
public class ChatCompletionResponse {
    private Long sessionId;
    private String sessionCode;
    private Long userMessageId;
    private Long assistantMessageId;
    private String reply;
    private String modelCode;
}
