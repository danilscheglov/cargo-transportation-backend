/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     03.06.2025 23:53:59                          */
/*==============================================================*/

/*==============================================================*/
/* Table: car                                                   */
/*==============================================================*/
create table car
(
    car_id                    SERIAL         not null,
    user_id                   INT4           not null,
    car_number                VARCHAR(20)    not null,
    car_model                 VARCHAR(50)    not null,
    car_brand                 VARCHAR(50)    not null,
    car_capacity              DECIMAL(10, 2) not null,
    car_mileage               INT4           not null,
    car_condition             VARCHAR(20)    not null,
    car_last_maintenance_date DATE null,
    constraint PK_CAR primary key (car_id)
);

/*==============================================================*/
/* Index: car_PK                                                */
/*==============================================================*/
create unique index car_PK on car (
                                   car_id
    );

/*==============================================================*/
/* Index: to_manage_FK                                          */
/*==============================================================*/
create index to_manage_FK on car (
                                  user_id
    );

/*==============================================================*/
/* Table: cargo                                                 */
/*==============================================================*/
create table cargo
(
    cargo_id     SERIAL         not null,
    order_id     INT4           not null,
    cargo_type   VARCHAR(50)    not null,
    cargo_weight DECIMAL(10, 2) not null,
    cargo_volume DECIMAL(10, 2) not null,
    constraint PK_CARGO primary key (cargo_id)
);

/*==============================================================*/
/* Index: cargo_PK                                              */
/*==============================================================*/
create unique index cargo_PK on cargo (
                                       cargo_id
    );

/*==============================================================*/
/* Index: contains_FK                                           */
/*==============================================================*/
create index contains_FK on cargo (
                                   order_id
    );

/*==============================================================*/
/* Table: maintenance_request                                   */
/*==============================================================*/
create table maintenance_request
(
    maintenance_request_id     SERIAL      not null,
    car_id                     INT4        not null,
    user_id                    INT4        not null,
    created_at                 DATE        not null,
    service_type               VARCHAR(50) not null,
    maintenance_request_status VARCHAR(20) not null,
    maintenance_request_note   TEXT null,
    constraint PK_MAINTENANCE_REQUEST primary key (maintenance_request_id)
);

/*==============================================================*/
/* Index: maintenance_request_PK                                */
/*==============================================================*/
create unique index maintenance_request_PK on maintenance_request (
                                                                   maintenance_request_id
    );

/*==============================================================*/
/* Index: refers_to_FK                                          */
/*==============================================================*/
create index refers_to_FK on maintenance_request (
                                                  car_id
    );

/*==============================================================*/
/* Index: to_create_FK                                          */
/*==============================================================*/
create index to_create_FK on maintenance_request (
                                                  user_id
    );

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table "order"
(
    order_id         SERIAL       not null,
    user_id          INT4         not null,
    car_id           INT4 null,
    order_startpoint VARCHAR(255) not null,
    order_endpoint   VARCHAR(255) not null,
    created_at       DATE         not null,
    order_status     VARCHAR(20)  not null,
    constraint PK_ORDER primary key (order_id)
);

/*==============================================================*/
/* Index: order_PK                                              */
/*==============================================================*/
create unique index order_PK on "order" (
                                         order_id
    );

/*==============================================================*/
/* Index: execute_FK                                            */
/*==============================================================*/
create index execute_FK on "order" (
                                    user_id
    );

/*==============================================================*/
/* Index: performs_FK                                           */
/*==============================================================*/
create index performs_FK on "order" (
                                     car_id
    );

/*==============================================================*/
/* Table: "user"                                                */
/*==============================================================*/
create table "user"
(
    user_id         SERIAL       not null,
    user_surname    VARCHAR(100) not null,
    user_name       VARCHAR(100) not null,
    user_patronymic VARCHAR(100) null,
    user_phone      VARCHAR(15)  not null,
    user_email      VARCHAR(100) not null,
    user_password   VARCHAR(255) not null,
    user_role       VARCHAR(12)  not null,
    constraint PK_USER primary key (user_id)
);

/*==============================================================*/
/* Index: user_PK                                               */
/*==============================================================*/
create unique index user_PK on "user" (
                                       user_id
    );

alter table car
    add constraint FK_CAR_TO_MANAGE_USER foreign key (user_id)
        references "user" (user_id)
        on delete restrict on update restrict;

alter table cargo
    add constraint FK_CARGO_CONTAINS_ORDER foreign key (order_id)
        references "order" (order_id)
        on delete restrict on update restrict;

alter table maintenance_request
    add constraint FK_MAINTENA_REFERS_TO_CAR foreign key (car_id)
        references car (car_id)
        on delete restrict on update restrict;

alter table maintenance_request
    add constraint FK_MAINTENA_TO_CREATE_USER foreign key (user_id)
        references "user" (user_id)
        on delete restrict on update restrict;

alter table "order"
    add constraint FK_ORDER_EXECUTE_USER foreign key (user_id)
        references "user" (user_id)
        on delete restrict on update restrict;

alter table "order"
    add constraint FK_ORDER_PERFORMS_CAR foreign key (car_id)
        references car (car_id)
        on delete restrict on update restrict;

