create database banks;
use banks;

create table user (
                      user_id integer not null auto_increment primary key,
                      name varchar(45),
                      surname varchar(45)
);

create table account (
                         account_id integer not null auto_increment primary key,
                         account integer,
                         user_id integer,
                         CONSTRAINT account_user_fk
                             FOREIGN KEY (user_id)  REFERENCES user (user_id)
);

insert into user (name, surname) values ('ivan', 'petrov');
insert into user (name, surname) values ('ivan', 'ivanov');
insert into user (name, surname) values ('dmitriy', 'sokolov');
insert into user (name, surname) values ('alex', 'bliznets');
insert into user (name, surname) values ('andrei', 'shevcov');
insert into user (name, surname) values ('svelana', 'petrova');
insert into user (name, surname) values ('valentina', 'lastochkina');
insert into user (name, surname) values ('ekaterina', 'smoliakova');
insert into user (name, surname) values ('petr', 'shilov');
insert into user (name, surname) values ('natallia', 'abibok');


insert into account (account, user_id) values (50, 1);
insert into account (account, user_id) values (43, 2);
insert into account (account, user_id) values (77, 3);
insert into account (account, user_id) values (10, 4);
insert into account (account, user_id) values (35, 5);
insert into account (account, user_id) values (80, 6);
insert into account (account, user_id) values (12, 7);
insert into account (account, user_id) values (19, 8);
insert into account (account, user_id) values (33, 9);
insert into account (account, user_id) values (70, 10);
insert into account (account, user_id) values (45, 5);
insert into account (account, user_id) values (55, 7);
insert into account (account, user_id) values (10, 10);
