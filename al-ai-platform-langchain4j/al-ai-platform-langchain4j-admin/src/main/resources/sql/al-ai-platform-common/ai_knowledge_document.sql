USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `ai_knowledge_document`;

CREATE TABLE `ai_knowledge_document`
(
    `id`            bigint auto_increment comment '主键ID',
    `create_time`   datetime     default current_timestamp not null comment '创建时间',
    `modify_time`   datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `kb_id`         bigint       not null comment '所属知识库ID',
    `doc_code`      varchar(64)  not null comment '文档编码',
    `doc_name`      varchar(256) not null comment '文档名称',
    `doc_type`      varchar(32)  default 'TEXT' comment '类型:TEXT/PDF/MD/URL',
    `content`       mediumtext   default null comment '文本内容(小文档直存)',
    `file_id`       bigint       default null comment '关联 common_binary_file.id',
    `chunk_count`   int          not null default 0 comment '分块数量',
    `status`        tinyint      not null default 1 comment '状态:1启用,0停用,2处理中',
    `ext_info`      text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_kb_doc_code` (`kb_id`, `doc_code`),
    key `idx_kb_id` (`kb_id`)
) engine = InnoDB
  default charset = utf8mb4 comment ='知识库文档表';
