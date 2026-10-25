USE `al-ai-platform-common`;

DROP TABLE IF EXISTS `common_binary_file`;

CREATE TABLE `common_binary_file`
(
    `id`          bigint auto_increment comment '主键ID',
    `create_time` datetime         default current_timestamp not null comment '创建时间',
    `modify_time` datetime         default current_timestamp not null on update current_timestamp comment '修改时间',
    `hash_value`  binary(32)       not null comment '文件Hash(SHA-256=32字节)',
    `file_data`   longblob         not null comment '文件二进制内容',
    `file_name`   varchar(256)     default null comment '原始文件名',
    `content_type` varchar(128)    default null comment 'MIME类型',
    `file_size`   bigint           default null comment '文件大小(字节)',
    `status`      tinyint unsigned not null default 1 comment '状态:1有效,0无效',
    `ext_info`    text             default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_hash_value` (`hash_value`)
) engine = InnoDB
  default charset = utf8mb4 comment ='通用二进制文件存储表';
