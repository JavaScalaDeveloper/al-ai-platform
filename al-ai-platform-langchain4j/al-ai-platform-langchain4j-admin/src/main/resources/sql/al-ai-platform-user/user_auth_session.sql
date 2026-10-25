USE `al-ai-platform-user`;

DROP TABLE IF EXISTS `user_auth_session`;

CREATE TABLE `user_auth_session`
(
    `id`          bigint auto_increment comment '主键ID',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `modify_time` datetime     default current_timestamp not null on update current_timestamp comment '修改时间',
    `token`       varchar(128) not null comment '登录令牌（与 Authorization 头一致）',
    `expire_time` datetime     not null comment '过期时间',
    `user_type`   varchar(32)  not null default 'ADMIN' comment '用户类型:ADMIN/USER',
    `user_info`   varchar(512) not null comment '用户展示信息 JSON（id/username/nickname/avatar）',
    primary key (`id`),
    key `idx_create_time` (`create_time`),
    key `idx_modify_time` (`modify_time`),
    unique key `uk_token` (`token`),
    key `idx_expire_time` (`expire_time`)
) engine = InnoDB
  default charset = utf8mb4 comment ='登录会话表';
