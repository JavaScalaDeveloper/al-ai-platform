package com.al.ai.platform.core.biz.chat.dto;

import lombok.Data;

@Data
public class ChatCompletionRequest {
    /** 已有会话 ID；为空则新建 */
    private Long sessionId;
    private String sessionCode;
    private String modelCode;
    private String title;
    private String message;
    private String systemPrompt;
}
