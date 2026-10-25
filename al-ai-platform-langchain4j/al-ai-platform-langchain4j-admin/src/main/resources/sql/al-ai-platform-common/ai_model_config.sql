USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_model_config`;

CREATE TABLE `ai_model_config`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `model_code`    varchar(64)  not null comment '模型编码(唯一，创建后不可改)',
    `model_name`    varchar(128) not null comment '模型显示名',
    `provider`      varchar(64)  not null comment '供应商:OPENAI/QWEN/OLLAMA/CUSTOM',
    `base_url`      varchar(512) default null comment 'API Base URL',
    `api_key`       varchar(256) default null comment 'API Key(可环境变量覆盖)',
    `model_id`      varchar(128) not null comment '上游模型ID，如 gpt-4o-mini',
    `temperature`   decimal(3, 2) default 0.70 comment '默认温度',
    `max_tokens`    int          default 4096 comment '默认最大输出token',
    `timeout_sec`   int          default 120 comment '超时秒数',
    `status`        tinyint      not null default 1 comment '状态:1启用,0停用',
    `is_default`    tinyint      not null default 0 comment '是否默认模型:1是,0否',
    `description`   varchar(512) default null comment '描述',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_model_code` (`model_code`),
    key `idx_provider_status` (`provider`, `status`)
) engine = InnoDB
  default charset = utf8mb4 comment ='AI模型配置表';
