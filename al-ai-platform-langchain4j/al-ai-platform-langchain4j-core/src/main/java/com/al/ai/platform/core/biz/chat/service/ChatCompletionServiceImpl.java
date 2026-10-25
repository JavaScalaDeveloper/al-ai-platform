package com.al.ai.platform.core.biz.chat.service;

import cn.hutool.core.util.IdUtil;
import com.al.ai.platform.core.biz.chat.dto.ChatCompletionRequest;
import com.al.ai.platform.core.biz.chat.dto.ChatCompletionResponse;
import com.al.ai.platform.core.biz.chat.entity.AiChatMessage;
import com.al.ai.platform.core.biz.chat.entity.AiChatSession;
import com.al.ai.platform.core.biz.chat.mapper.AiChatMessageMapper;
import com.al.ai.platform.core.biz.chat.mapper.AiChatSessionMapper;
import com.al.ai.platform.core.biz.model.entity.AiModelConfig;
import com.al.ai.platform.core.biz.model.mapper.AiModelConfigMapper;
import com.al.ai.platform.core.common.exception.BusinessException;
import com.al.ai.platform.core.common.result.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatCompletionServiceImpl implements ChatCompletionService {

    private final AiChatSessionMapper sessionMapper;
    private final AiChatMessageMapper messageMapper;
    private final AiModelConfigMapper modelConfigMapper;

    @Value("${alai.llm.default-base-url:http://127.0.0.1:11434/v1}")
    private String defaultBaseUrl;

    @Value("${alai.llm.default-api-key:sk-local}")
    private String defaultApiKey;

    @Value("${alai.llm.default-model:qwen2.5}")
    private String defaultModelId;

    @Override
    @Transactional(transactionManager = "commonTransactionManager")
    public ChatCompletionResponse chat(ChatCompletionRequest request) {
        if (request == null || !StringUtils.hasText(request.getMessage())) {
            throw new BusinessException(ResultCode.PARAMS_VALIDATION_ERROR, "消息内容不能为空");
        }

        AiModelConfig model = resolveModel(request.getModelCode());
        AiChatSession session = resolveOrCreateSession(request, model.getModelCode());

        AiChatMessage userMsg = new AiChatMessage();
        userMsg.setSessionId(session.getId());
        userMsg.setRole("USER");
        userMsg.setContent(request.getMessage());
        userMsg.setModelCode(model.getModelCode());
        userMsg.setStatus(1);
        messageMapper.insert(userMsg);

        List<ChatMessage> history = buildHistory(session.getId(), request.getSystemPrompt());
        ChatLanguageModel chatModel = buildChatModel(model);
        Response<AiMessage> response = chatModel.generate(history);
        String reply = response.content() == null ? "" : response.content().text();

        AiChatMessage assistantMsg = new AiChatMessage();
        assistantMsg.setSessionId(session.getId());
        assistantMsg.setRole("ASSISTANT");
        assistantMsg.setContent(reply);
        assistantMsg.setModelCode(model.getModelCode());
        assistantMsg.setStatus(1);
        messageMapper.insert(assistantMsg);

        session.setMessageCount((session.getMessageCount() == null ? 0 : session.getMessageCount()) + 2);
        if ("新对话".equals(session.getTitle()) && StringUtils.hasText(request.getMessage())) {
            String title = request.getMessage().length() > 40
                    ? request.getMessage().substring(0, 40) + "..."
                    : request.getMessage();
            session.setTitle(title);
        }
        sessionMapper.updateById(session);

        ChatCompletionResponse result = new ChatCompletionResponse();
        result.setSessionId(session.getId());
        result.setSessionCode(session.getSessionCode());
        result.setUserMessageId(userMsg.getId());
        result.setAssistantMessageId(assistantMsg.getId());
        result.setReply(reply);
        result.setModelCode(model.getModelCode());
        return result;
    }

    private AiModelConfig resolveModel(String modelCode) {
        AiModelConfig model = null;
        if (StringUtils.hasText(modelCode)) {
            model = modelConfigMapper.selectOne(new LambdaQueryWrapper<AiModelConfig>()
                    .eq(AiModelConfig::getModelCode, modelCode)
                    .eq(AiModelConfig::getStatus, 1)
                    .last("limit 1"));
        }
        if (model == null) {
            model = modelConfigMapper.selectOne(new LambdaQueryWrapper<AiModelConfig>()
                    .eq(AiModelConfig::getIsDefault, 1)
                    .eq(AiModelConfig::getStatus, 1)
                    .last("limit 1"));
        }
        if (model == null) {
            // 无库配置时使用 yml 默认（便于本地先跑通）
            model = new AiModelConfig();
            model.setModelCode("default");
            model.setModelName("Default");
            model.setProvider("OPENAI");
            model.setBaseUrl(defaultBaseUrl);
            model.setApiKey(defaultApiKey);
            model.setModelId(defaultModelId);
            model.setTemperature(new java.math.BigDecimal("0.70"));
            model.setMaxTokens(4096);
            model.setTimeoutSec(120);
            model.setStatus(1);
        }
        return model;
    }

    private AiChatSession resolveOrCreateSession(ChatCompletionRequest request, String modelCode) {
        if (request.getSessionId() != null) {
            AiChatSession session = sessionMapper.selectById(request.getSessionId());
            if (session == null) {
                throw new BusinessException(ResultCode.DATA_NOT_FOUND, "会话不存在");
            }
            return session;
        }
        if (StringUtils.hasText(request.getSessionCode())) {
            AiChatSession session = sessionMapper.selectOne(new LambdaQueryWrapper<AiChatSession>()
                    .eq(AiChatSession::getSessionCode, request.getSessionCode())
                    .last("limit 1"));
            if (session != null) {
                return session;
            }
        }
        AiChatSession session = new AiChatSession();
        session.setSessionCode(StringUtils.hasText(request.getSessionCode())
                ? request.getSessionCode()
                : "S" + IdUtil.fastSimpleUUID());
        session.setTitle(StringUtils.hasText(request.getTitle()) ? request.getTitle() : "新对话");
        session.setModelCode(modelCode);
        session.setUserType("ADMIN");
        session.setMessageCount(0);
        session.setStatus(1);
        sessionMapper.insert(session);
        return session;
    }

    private List<ChatMessage> buildHistory(Long sessionId, String systemPrompt) {
        List<ChatMessage> messages = new ArrayList<>();
        if (StringUtils.hasText(systemPrompt)) {
            messages.add(SystemMessage.from(systemPrompt));
        }
        List<AiChatMessage> rows = messageMapper.selectList(new LambdaQueryWrapper<AiChatMessage>()
                .eq(AiChatMessage::getSessionId, sessionId)
                .eq(AiChatMessage::getStatus, 1)
                .orderByAsc(AiChatMessage::getId)
                .last("limit 40"));
        for (AiChatMessage row : rows) {
            if ("SYSTEM".equalsIgnoreCase(row.getRole())) {
                messages.add(SystemMessage.from(row.getContent()));
            } else if ("ASSISTANT".equalsIgnoreCase(row.getRole())) {
                messages.add(AiMessage.from(row.getContent()));
            } else {
                messages.add(UserMessage.from(row.getContent()));
            }
        }
        return messages;
    }

    private ChatLanguageModel buildChatModel(AiModelConfig model) {
        String baseUrl = StringUtils.hasText(model.getBaseUrl()) ? model.getBaseUrl() : defaultBaseUrl;
        String apiKey = StringUtils.hasText(model.getApiKey()) ? model.getApiKey() : defaultApiKey;
        String modelId = StringUtils.hasText(model.getModelId()) ? model.getModelId() : defaultModelId;
        double temperature = model.getTemperature() == null ? 0.7 : model.getTemperature().doubleValue();
        int maxTokens = model.getMaxTokens() == null ? 4096 : model.getMaxTokens();
        int timeout = model.getTimeoutSec() == null ? 120 : model.getTimeoutSec();

        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modelId)
                .temperature(temperature)
                .maxTokens(maxTokens)
                .timeout(Duration.ofSeconds(timeout))
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
