create table if not exists ayang_website.user
(
    id            bigint                             not null comment '主键'
        primary key,
    user_name     varchar(256)                       null comment '用户名称',
    user_account  varchar(256)                       null comment '账户',
    avatar_url    varchar(1024)                      null comment '头像url',
    gender        tinyint                            null comment '性别',
    user_password varchar(512)                       not null comment '密码',
    phone         varchar(11)                        null comment '电话',
    email         varchar(256)                       null comment '邮箱',
    user_status   int      default 0                 null comment '用户状态(0:正常...)',
    role          int      default 0                 not null comment '角色（0：普通用户，1：管理员）',
    check_code    varchar(512)                       null comment '校验编码',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint  default 0                 not null comment '逻辑删除(0：存在，1：删除）'
)
    comment '用户表';

create table if not exists user_check_code
(
    id           bigint                             not null comment '主键'
        primary key,
    check_code   varchar(512)                       not null comment '校验编码',
    check_status tinyint  default 0                 not null comment '校验状态(0:未使用，1：已使用)',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete    tinyint  default 0                 not null comment '删除标识（0：未删除，1：已删除）'
);