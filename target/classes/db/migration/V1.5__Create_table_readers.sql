create table if not exists readers(
    id_reader serial,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    fav_booksgender varchar(50) not null,
    constraint pk_readers primary key(id_reader)
);

