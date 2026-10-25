package com.al.ai.platform.admin.controller;

import com.al.ai.platform.core.biz.chat.dto.AiChatMessageRequest;
import com.al.ai.platform.core.biz.chat.dto.AiChatMessageResponse;
import com.al.ai.platform.core.biz.chat.dto.AiChatSessionRequest;
import com.al.ai.platform.core.biz.chat.dto.AiChatSessionResponse;
import com.al.ai.platform.core.biz.chat.dto.ChatCompletionRequest;
import com.al.ai.platform.core.biz.chat.dto.ChatCompletionResponse;
import com.al.ai.platform.core.biz.chat.service.AiChatMessageService;
import com.al.ai.platform.core.biz.chat.service.AiChatSessionService;
import com.al.ai.platform.core.biz.chat.service.ChatCompletionService;
import com.al.ai.platform.core.common.dto.IdRequest;
import com.al.ai.platform.core.common.dto.PageResult;
import com.al.ai.platform.core.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/chat")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatSessionService sessionService;
    private final AiChatMessageService messageService;
    private final ChatCompletionService chatCompletionService;

    @PostMapping("/session/list")
    public Result<PageResult<AiChatSessionResponse>> sessionList(@RequestBody AiChatSessionRequest request) {
        return Result.success(sessionService.pageQuery(request));
    }

    @PostMapping("/session/detail")
    public Result<AiChatSessionResponse> sessionDetail(@RequestBody IdRequest request) {
        return Result.success(sessionService.getById(request.getId()));
    }

    @PostMapping("/session/delete")
    public Result<Void> sessionDelete(@RequestBody IdRequest request) {
        sessionService.deleteById(request.getId());
        return Result.success();
    }

    @PostMapping("/message/list")
    public Result<PageResult<AiChatMessageResponse>> messageList(@RequestBody AiChatMessageRequest request) {
        return Result.success(messageService.pageQuery(request));
    }

    @PostMapping("/completion")
    public Result<ChatCompletionResponse> completion(@RequestBody ChatCompletionRequest request) {
        return Result.success(chatCompletionService.chat(request));
    }
}
