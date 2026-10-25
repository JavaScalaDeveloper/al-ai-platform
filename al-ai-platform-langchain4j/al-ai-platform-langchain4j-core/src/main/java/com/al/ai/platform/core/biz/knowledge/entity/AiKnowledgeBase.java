package com.al.ai.platform.core.biz.knowledge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_knowledge_base")
public class AiKnowledgeBase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String kbCode;
    private String kbName;
    private String embeddingModel;
    private String description;
    private Integer docCount;
    private Integer status;
    private String extInfo;
}
