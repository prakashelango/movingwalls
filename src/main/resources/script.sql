create table user
(
    id         bigint not null auto_increment,
    age        integer,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    salary     bigint,
    username   varchar(255),
    primary key (id)
) engine = MyISAM;

