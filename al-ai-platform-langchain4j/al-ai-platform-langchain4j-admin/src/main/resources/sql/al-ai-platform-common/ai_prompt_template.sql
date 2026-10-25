USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_prompt_template`;

CREATE TABLE `ai_prompt_template`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `prompt_code`   varchar(64)  not null comment '模板编码(唯一)',
    `prompt_name`   varchar(128) not null comment '模板名称',
    `category`      varchar(64)  default 'GENERAL' comment '分类',
    `system_prompt` mediumtext   default null comment '系统提示词',
    `user_prompt`   mediumtext   default null comment '用户提示词模板(可含占位符)',
    `variables`     text         default null comment '变量定义 JSON',
    `status`        tinyint      not null default 1 comment '状态:1启用,0停用',
    `description`   varchar(512) default null comment '描述',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_prompt_code` (`prompt_code`),
    key `idx_category_status` (`category`, `status`)
) engine = InnoDB
  default charset = utf8mb4 comment ='Prompt模板表';
