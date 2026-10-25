USE `al-ai-platform-user`;

DROP TABLE IF EXISTS `admin_account`;

CREATE TABLE `admin_account`
(
    `id`          bigint auto_increment comment '主键ID',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `modify_time` datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `username`    varchar(64)  not null comment '登录用户名',
    `password`    varchar(128) not null comment '密码(开发态可明文；生产请存BCrypt)',
    `nickname`    varchar(64)  default null comment '昵称',
    `avatar`      varchar(512) default null comment '头像URL',
    `status`      tinyint      not null default 1 comment '状态:1启用,0停用',
    `ext_info`    text         default null comment '拓展信息(JSON)',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_username` (`username`)
) engine = InnoDB
  default charset = utf8mb4 comment ='管理端账号表';

INSERT INTO `admin_account` (`username`, `password`, `nickname`, `status`)
VALUES ('admin', 'admin123', '超级管理员', 1);
