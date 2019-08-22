connect 'jdbc:derby://localhost:1527/SummaryTask4;create=true;user=user;password=pass';
drop table reviewsCars;
drop table orders;
drop table cars;
drop table users;
drop table roles;


CREATE TABLE roles(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	role varchar(20) not null unique
);

CREATE TABLE users(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	login varchar(15) not null unique,
	password varchar(15) not null,
	role_id INTEGER  not null REFERENCES roles(id),
	ban BOOLEAN not null WITH DEFAULT false
);

CREATE TABLE cars(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	producer varchar(20),
	make varchar(20),
	rank varchar(20),	
	price REAL,
	year int,
	imageLocAdress varchar(40),
	active boolean not null with default true
);

CREATE TABLE orders(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id),
	car_id INTEGER NOT NULL REFERENCES cars(id),
	start_rent DATE NOT NULL,
	finish_rent DATE NOT NULL,	
	state varchar(20) not null with default 'new order',
	priceTotal DECIMAL not null with default 0,
	driver boolean with default false,
	priceCrush DECIMAL not null with default 0
);


CREATE TABLE reviewsCars(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id),
	car_id INTEGER NOT NULL REFERENCES cars(id),
	dateReview DATE NOT NULL,
	review varchar(500) not null
);

CREATE TABLE ordersCrush(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	priceCrush DECIMAL not null with default 0,
	stateCrus varchar(40) not null,
	orders_id INTEGER NOT NULL REFERENCES orders(id)
);
insert into roles values (DEFAULT,'client');
insert into roles values (DEFAULT,'manager');
insert into roles values (DEFAULT,'admin');

insert into users(login,password,role_id) select 'admin','admin',roles.id from roles where roles.role='admin';
insert into users(login,password,role_id) select 'man','man',roles.id from roles where roles.role='manager';

