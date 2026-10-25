package com.al.ai.platform.core.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_model_config")
public class AiModelConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String modelCode;
    private String modelName;
    private String provider;
    private String baseUrl;
    private String apiKey;
    private String modelId;
    private BigDecimal temperature;
    private Integer maxTokens;
    private Integer timeoutSec;
    private Integer status;
    private Integer isDefault;
    private String description;
    private String extInfo;
}
