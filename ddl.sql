create table if not exists ki_iot.t_action_code
(
    id                    bigint(64)    not null
        primary key,
    project_id            varchar(255)  null,
    product_key           varchar(255)  not null,
    device_name           varchar(255)  not null,
    device_secret         varchar(255)  null,
    device_signature_code varchar(1024) null
);

