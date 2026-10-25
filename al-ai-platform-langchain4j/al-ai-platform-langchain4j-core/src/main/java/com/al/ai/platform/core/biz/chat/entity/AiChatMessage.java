package com.al.ai.platform.core.biz.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_chat_message")
public class AiChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long sessionId;
    private String role;
    private String content;
    private Integer tokenUsage;
    private String modelCode;
    private Integer status;
    private String extInfo;
}
