USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_chat_message`;

CREATE TABLE `ai_chat_message`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `session_id`    bigint       not null comment '所属会话ID',
    `role`          varchar(32)  not null comment '角色:SYSTEM/USER/ASSISTANT/TOOL',
    `content`       mediumtext   not null comment '消息内容',
    `token_usage`   int          default null comment '本条消耗token(估算)',
    `model_code`    varchar(64)  default null comment '实际使用模型',
    `status`        tinyint      not null default 1 comment '状态:1正常,0删除',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    key `idx_session_id` (`session_id`)
) engine = InnoDB
  default charset = utf8mb4 comment ='对话消息表';
