create table if not exists artist
(
	id bigserial not null
		constraint artist_pkey
			primary key,
	name varchar(255) not null
)
;

alter table artist owner to postgres
;

create table if not exists events_artists
(
	event_id bigint not null,
	artist_id bigint not null
		constraint fkdlylh4hkk6b010c04cs0gadh4
			references artist,
	constraint events_artists_pkey
		primary key (event_id, artist_id)
)
;

alter table events_artists owner to postgres
;

create table if not exists events_places
(
	place_id bigint not null,
	artist_id bigint not null,
	constraint events_places_pkey
		primary key (place_id, artist_id)
)
;

alter table events_places owner to postgres
;

create table if not exists organization
(
	id bigserial not null
		constraint organization_pkey
			primary key,
	customer_id varchar(255) not null,
	email varchar(255) not null,
	location varchar(255) not null,
	name varchar(255) not null
		constraint uk_8j5y8ipk73yx2joy9yr653c9t
			unique,
	phone varchar(255) not null,
	visible boolean not null
)
;

alter table organization owner to postgres
;

create table if not exists abstract_event
(
	type_discrimination varchar(31) not null,
	id bigserial not null
		constraint abstract_event_pkey
			primary key,
	artists varchar(255) not null,
	date bigint not null,
	description varchar(255) not null,
	location varchar(255) not null,
	name varchar(255) not null,
	photo_url varchar(255) not null,
	type varchar(255),
	visible boolean not null,
	organization_id bigint
		constraint fknppwj34ym9sidbqlgnqk8wip9
			references organization
)
;

alter table abstract_event owner to postgres
;

create table if not exists age_constrain_event
(
	min_age integer not null,
	id bigint not null
		constraint age_constrain_event_pkey
			primary key
		constraint fkqqixdlr7sqfo99r0ocgponhar
			references abstract_event
)
;

alter table age_constrain_event owner to postgres
;

create table if not exists cinema_event
(
	id bigint not null
		constraint cinema_event_pkey
			primary key
		constraint fkcgqpw51gp34qegqw8w6beqhk9
			references age_constrain_event
)
;

alter table cinema_event owner to postgres
;

create table if not exists cover_concert_event
(
	id bigint not null
		constraint cover_concert_event_pkey
			primary key
		constraint fk854dou8bt4bfg3ufghr1587kj
			references abstract_event
)
;

alter table cover_concert_event owner to postgres
;

create table if not exists original_concert_event
(
	id bigint not null
		constraint original_concert_event_pkey
			primary key
		constraint fkkosvrko0qryd0epmmogix0wsx
			references abstract_event
)
;

alter table original_concert_event owner to postgres
;

create table if not exists place
(
	id bigserial not null
		constraint place_pkey
			primary key,
	number integer not null,
	price double precision not null,
	row integer not null,
	section_type varchar(255),
	status varchar(255) not null,
	event_id bigint
		constraint fk9eck3esqyffuspp3j0s4t07e2
			references abstract_event
)
;

alter table place owner to postgres
;

create table if not exists theatre_event
(
	id bigint not null
		constraint theatre_event_pkey
			primary key
		constraint fk4cicgv2tjrydc9facskjsnsmw
			references age_constrain_event
)
;

alter table theatre_event owner to postgres
;

