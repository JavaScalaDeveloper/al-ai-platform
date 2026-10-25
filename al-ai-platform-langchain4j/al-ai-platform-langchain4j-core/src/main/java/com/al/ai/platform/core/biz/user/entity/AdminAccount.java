package com.al.ai.platform.core.biz.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admin_account")
public class AdminAccount {
    @TableId(type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private Integer status;
    private String extInfo;
}
