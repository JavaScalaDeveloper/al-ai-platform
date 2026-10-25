package com.al.ai.platform.core.biz.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ai_chat_session")
public class AiChatSession {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String sessionCode;
    private String title;
    private String modelCode;
    private Long userId;
    private String userType;
    private Long kbId;
    private Integer messageCount;
    private Integer status;
    private String extInfo;
}
