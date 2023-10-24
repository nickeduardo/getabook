create table if not exists books(
    id_book serial,
    book_name varchar(50) not null,
    author_name varchar(50) not null,
    publisher int not null,
    constraint fk_publisher foreign key (publisher) references publishers(id_publisher),
    constraint pk_books primary key(id_book)
);