# 数据库初始化

-- 创建库
create database if not exists xhapi;

-- 切换库
use xhapi;

-- 用户表
create table if not exists user
(
    id            bigint auto_increment comment 'id' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    gender        varchar(10)                            null comment '性别 0-男 1-女',
    user_role     varchar(128) default 'user'            not null comment '用户角色：user/admin/ban',
    access_key     varchar(256)                           null comment 'accessKey',
    secret_key     varchar(256)                           null comment 'secretKey',
    points        bigint       default 30                not null comment '用户积分,注册送30点',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint      default 0                 not null comment '是否删除',
    index idx_user_account (user_account)
) comment '用户';

-- 接口信息
create table if not exists interface_info
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)                       not null comment '接口名称',
    description varchar(256)                       null comment '接口描述',
    url         varchar(512)                       not null comment '接口地址',
    method      varchar(256)                       not null comment '请求类型',
    request_params text                            null comment '接口请求参数',
    response_params text                           null comment '接口响应参数',
    request_header text                            null comment '请求头',
    response_header text                           null comment '响应头',
    result_format varchar(512) default 'JSON'      null comment '返回格式(JSON等等)',
    total_invokes bigint default 0                 not null comment '总调用次数',
    request_example text                           null comment '请求示例',
    status      int      default 0                 not null comment '状态(0 关闭 1 开启)',
    consume_points bigint default 0                null comment '消耗积分',
    user_id     bigint                             not null comment '创建人',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '接口信息';

-- 用户调用接口表
create table if not exists user_interface_info
(
    id                bigint auto_increment comment 'id' primary key,
    user_id           bigint                             not null comment '调用用户 id',
    interface_info_id bigint                             not null comment '接口 id',
    total_invokes     bigint   default 0              not null comment '总调用次数',
    status            int      default 0                 not null comment '0-正常，1-禁用',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted           tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口';

-- 用户签到表
create table if not exists user_check_in
(
    id           bigint auto_increment comment 'id' primary key,
    user_id      bigint                             not null comment '签到人',
    description  varchar(256)                       null comment '描述',
    add_points   bigint   default 10                not null comment '签到增加积分个数',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户签到';



