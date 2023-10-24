create table if not exists publishers(
    id_publisher serial,
    publisher_name varchar(50) not null,
    contact varchar(20) not null,
    establishment_year int not null,
    constraint pk_publishers primary key(id_publisher)
);