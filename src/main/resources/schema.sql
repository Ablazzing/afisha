--Создание схемы
--create schema if not exists application;

--Создание таблицы тип событий
create table if not exists application.event_type (id serial primary key,
    name varchar(100) NOT NULL UNIQUE);
--Заполнение таблицы тип событий
insert into application.event_type (name) values ('museum'),
    ('cinema'), ('theatre') ON CONFLICT DO NOTHING;

--Создание таблицы место проведения
create table if not exists application.place (
	id serial primary key,
	name varchar(100) NOT NULL,
	city varchar(100) NOT NULL,
	address varchar(100) NOT NULL
);

--Создание таблицы событие
create table if not exists application.event (
	id serial primary key,
	name varchar(100) NOT NULL,
	event_type_id int references application.event_type(id) NOT NULL,
	place_id int references application.event_type(id) NOT NULL,
	event_date timestamp NOT NULL
);

--Создание таблицы билет
create table if not exists application.ticket (
	id serial primary key,
	client_email varchar(100),
	price numeric(10,2) check (price > 0),
	is_selled bool,
	event_id int references application.event(id)
);