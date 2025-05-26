/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     26.05.2025 18:34:43                          */
/*==============================================================*/

/*==============================================================*/
/* Table: car                                                   */
/*==============================================================*/
create table car
(
    car_id                    SERIAL         not null,
    driver_id                 INT4           not null,
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
/* Table: client                                                */
/*==============================================================*/
create table client
(
    client_id         SERIAL       not null,
    client_surname    VARCHAR(50)  not null,
    client_name       VARCHAR(50)  not null,
    client_patronymic VARCHAR(50) null,
    client_phone      VARCHAR(25)  not null,
    client_email      VARCHAR(100) not null,
    client_password   VARCHAR(255) not null,
    constraint PK_CLIENT primary key (client_id)
);

/*==============================================================*/
/* Table: dispatcher                                            */
/*==============================================================*/
create table dispatcher
(
    dispatcher_id         SERIAL       not null,
    dispatcher_surname    VARCHAR(50)  not null,
    dispatcher_name       VARCHAR(50)  not null,
    dispatcher_patronymic VARCHAR(50) null,
    dispatcher_phone      VARCHAR(25)  not null,
    dispatcher_email      VARCHAR(100) not null,
    dispatcher_password   VARCHAR(255) not null,
    constraint PK_DISPATCHER primary key (dispatcher_id)
);

/*==============================================================*/
/* Table: driver                                                */
/*==============================================================*/
create table driver
(
    driver_id         SERIAL       not null,
    driver_surname    VARCHAR(50)  not null,
    driver_name       VARCHAR(50)  not null,
    driver_patronymic VARCHAR(50) null,
    driver_phone      VARCHAR(25)  not null,
    driver_email      VARCHAR(100) not null,
    driver_password   VARCHAR(255) not null,
    driver_experience INT4         not null,
    constraint PK_DRIVER primary key (driver_id)
);

/*==============================================================*/
/* Table: flight                                                */
/*==============================================================*/
create table flight
(
    flight_id             SERIAL         not null,
    driver_id             INT4           not null,
    dispatcher_id         INT4           not null,
    car_id                INT4           not null,
    flight_startpoint     VARCHAR(255)   not null,
    flight_endpoint       VARCHAR(255)   not null,
    flight_distance       DECIMAL(10, 2) not null,
    flight_departure_date DATE           not null,
    constraint PK_FLIGHT primary key (flight_id)
);

/*==============================================================*/
/* Table: maintenance_request                                   */
/*==============================================================*/
create table maintenance_request
(
    maintenance_request_id     SERIAL      not null,
    car_id                     INT4        not null,
    mechanic_id                INT4        not null,
    filling_date               DATE        not null,
    service_type               VARCHAR(50) not null,
    maintenance_request_status VARCHAR(20) not null,
    maintenance_request_note   TEXT null,
    constraint PK_MAINTENANCE_REQUEST primary key (maintenance_request_id)
);

/*==============================================================*/
/* Table: mechanic                                              */
/*==============================================================*/
create table mechanic
(
    mechanic_id         SERIAL       not null,
    mechanic_surname    VARCHAR(50)  not null,
    mechanic_name       VARCHAR(50)  not null,
    mechanic_patronymic VARCHAR(50) null,
    mechanic_phone      VARCHAR(25)  not null,
    mechanic_email      VARCHAR(100) not null,
    mechanic_password   VARCHAR(255) not null,
    constraint PK_MECHANIC primary key (mechanic_id)
);

/*==============================================================*/
/* Table: operator                                              */
/*==============================================================*/
create table operator
(
    operator_id         SERIAL       not null,
    operator_surname    VARCHAR(50)  not null,
    operator_name       VARCHAR(50)  not null,
    operator_patronymic VARCHAR(50) null,
    operator_phone      VARCHAR(25)  not null,
    operator_email      VARCHAR(100) not null,
    operator_password   VARCHAR(255) not null,
    constraint PK_OPERATOR primary key (operator_id)
);

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table "order"
(
    order_id            SERIAL       not null,
    client_id           INT4         not null,
    operator_id         INT4         not null,
    flight_id           INT4 null,
    order_startpoint    VARCHAR(255) not null,
    order_endpoint      VARCHAR(255) not null,
    order_dispatch_date DATE         not null,
    order_delivery_date DATE         not null,
    order_status        VARCHAR(20)  not null,
    constraint PK_ORDER primary key (order_id)
);