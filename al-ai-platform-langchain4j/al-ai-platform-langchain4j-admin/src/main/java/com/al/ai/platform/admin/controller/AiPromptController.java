package com.al.ai.platform.admin.controller;

import com.al.ai.platform.core.biz.prompt.dto.AiPromptTemplateRequest;
import com.al.ai.platform.core.biz.prompt.dto.AiPromptTemplateResponse;
import com.al.ai.platform.core.biz.prompt.service.AiPromptTemplateService;
import com.al.ai.platform.core.common.dto.IdRequest;
import com.al.ai.platform.core.common.dto.PageResult;
import com.al.ai.platform.core.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/prompt")
@RequiredArgsConstructor
public class AiPromptController {

    private final AiPromptTemplateService promptTemplateService;

    @PostMapping("/list")
    public Result<PageResult<AiPromptTemplateResponse>> list(@RequestBody AiPromptTemplateRequest request) {
        return Result.success(promptTemplateService.pageQuery(request));
    }

    @PostMapping("/detail")
    public Result<AiPromptTemplateResponse> detail(@RequestBody IdRequest request) {
        return Result.success(promptTemplateService.getById(request.getId()));
    }

    @PostMapping("/create")
    public Result<Void> create(@RequestBody AiPromptTemplateRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        promptTemplateService.create(request);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody AiPromptTemplateRequest request) {
        promptTemplateService.update(request);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody IdRequest request) {
        promptTemplateService.deleteById(request.getId());
        return Result.success();
    }
}
