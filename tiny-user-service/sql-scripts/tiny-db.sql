create database tinydb;

create table tiny_url(
  tiny_url character varying(10) not null primary key,
  actual_url character varying(256) not null,
  user_id numeric not null,
  active boolean default true,
  created_on timestamp with time zone not null,
  updated_on timestamp with time zone
);

create table tiny_url_store(
   tiny_url character varying(10) not null primary key,
   used boolean default false,
   created_on timestamp with time zone not null,
   updated_on timestamp with time zone
);

create table tiny_user(
	tiny_user_id serial not null primary key,
	username character varying(20) not null,
	email character varying(50) not null,
	active boolean default true,
	created_on timestamp with time zone not null,
	updated_on timestamp with time zone
);

create table tiny_user_key(
	tiny_user_key_id serial not null primary key,
	tiny_user_id serial not null,
	api_key character varying(50) not null,
	active boolean default true,
	created_on timestamp with time zone not null,
	updated_on timestamp with time zone
);


drop table tiny_url;

drop table tiny_url_store;

drop table tiny_user;

drop table tiny_user_key;