USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_knowledge_base`;

CREATE TABLE `ai_knowledge_base`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `kb_code`       varchar(64)  not null comment '知识库编码(唯一)',
    `kb_name`       varchar(128) not null comment '知识库名称',
    `embedding_model` varchar(128) default null comment 'Embedding模型',
    `description`   varchar(512) default null comment '描述',
    `doc_count`     int          not null default 0 comment '文档数量',
    `status`        tinyint      not null default 1 comment '状态:1启用,0停用',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_kb_code` (`kb_code`)
) engine = InnoDB
  default charset = utf8mb4 comment ='知识库表';
