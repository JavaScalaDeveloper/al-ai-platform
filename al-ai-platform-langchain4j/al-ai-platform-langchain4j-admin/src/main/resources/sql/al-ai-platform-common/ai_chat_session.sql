USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_chat_session`;

CREATE TABLE `ai_chat_session`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `session_code`  varchar(64)  not null comment '会话编码(唯一)',
    `title`         varchar(256) default '新对话' comment '会话标题',
    `model_code`    varchar(64)  default null comment '使用的模型编码',
    `user_id`       bigint       default null comment '创建用户ID',
    `user_type`     varchar(32)  default 'ADMIN' comment '用户类型',
    `kb_id`         bigint       default null comment '关联知识库ID(可选)',
    `message_count` int          not null default 0 comment '消息条数',
    `status`        tinyint      not null default 1 comment '状态:1进行中,0已归档',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_session_code` (`session_code`),
    key `idx_user` (`user_type`, `user_id`)
) engine = InnoDB
  default charset = utf8mb4 comment ='对话会话表';
