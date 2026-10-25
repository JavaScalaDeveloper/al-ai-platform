package com.al.ai.platform.admin.controller;

import com.al.ai.platform.core.biz.model.dto.AiModelConfigRequest;
import com.al.ai.platform.core.biz.model.dto.AiModelConfigResponse;
import com.al.ai.platform.core.biz.model.service.AiModelConfigService;
import com.al.ai.platform.core.common.dto.IdRequest;
import com.al.ai.platform.core.common.dto.PageResult;
import com.al.ai.platform.core.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/model")
@RequiredArgsConstructor
public class AiModelController {

    private final AiModelConfigService aiModelConfigService;

    @PostMapping("/list")
    public Result<PageResult<AiModelConfigResponse>> list(@RequestBody AiModelConfigRequest request) {
        return Result.success(aiModelConfigService.pageQuery(request));
    }

    @PostMapping("/detail")
    public Result<AiModelConfigResponse> detail(@RequestBody IdRequest request) {
        return Result.success(aiModelConfigService.getById(request.getId()));
    }

    @PostMapping("/create")
    public Result<Void> create(@RequestBody AiModelConfigRequest request) {
        aiModelConfigService.create(request);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody AiModelConfigRequest request) {
        aiModelConfigService.update(request);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody IdRequest request) {
        aiModelConfigService.deleteById(request.getId());
        return Result.success();
    }
}
