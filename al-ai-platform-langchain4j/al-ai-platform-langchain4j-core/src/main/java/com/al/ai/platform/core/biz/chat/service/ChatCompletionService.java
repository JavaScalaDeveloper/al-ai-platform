package com.al.ai.platform.core.biz.chat.service;

import com.al.ai.platform.core.biz.chat.dto.ChatCompletionRequest;
import com.al.ai.platform.core.biz.chat.dto.ChatCompletionResponse;

public interface ChatCompletionService {
    ChatCompletionResponse chat(ChatCompletionRequest request);
}
