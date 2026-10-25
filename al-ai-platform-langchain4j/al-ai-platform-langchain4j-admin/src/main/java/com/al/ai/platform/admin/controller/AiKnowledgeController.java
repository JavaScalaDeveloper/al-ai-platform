package com.al.ai.platform.admin.controller;

import com.al.ai.platform.core.biz.knowledge.dto.AiKnowledgeBaseRequest;
import com.al.ai.platform.core.biz.knowledge.dto.AiKnowledgeBaseResponse;
import com.al.ai.platform.core.biz.knowledge.service.AiKnowledgeBaseService;
import com.al.ai.platform.core.common.dto.IdRequest;
import com.al.ai.platform.core.common.dto.PageResult;
import com.al.ai.platform.core.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/knowledge")
@RequiredArgsConstructor
public class AiKnowledgeController {

    private final AiKnowledgeBaseService knowledgeBaseService;

    @PostMapping("/list")
    public Result<PageResult<AiKnowledgeBaseResponse>> list(@RequestBody AiKnowledgeBaseRequest request) {
        return Result.success(knowledgeBaseService.pageQuery(request));
    }

    @PostMapping("/detail")
    public Result<AiKnowledgeBaseResponse> detail(@RequestBody IdRequest request) {
        return Result.success(knowledgeBaseService.getById(request.getId()));
    }

    @PostMapping("/create")
    public Result<Void> create(@RequestBody AiKnowledgeBaseRequest request) {
        if (request.getDocCount() == null) {
            request.setDocCount(0);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        knowledgeBaseService.create(request);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody AiKnowledgeBaseRequest request) {
        knowledgeBaseService.update(request);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody IdRequest request) {
        knowledgeBaseService.deleteById(request.getId());
        return Result.success();
    }
}
