package com.al.ai.platform.core.biz.prompt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_prompt_template")
public class AiPromptTemplate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String promptCode;
    private String promptName;
    private String category;
    private String systemPrompt;
    private String userPrompt;
    private String variables;
    private Integer status;
    private String description;
    private String extInfo;
}
