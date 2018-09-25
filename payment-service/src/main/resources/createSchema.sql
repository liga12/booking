create table if not exists payment
(
	id bigserial not null
		constraint payment_pkey
			primary key,
	token varchar(255) not null
)
;

alter table payment owner to postgres
;

create table if not exists order_client
(
	id bigserial not null
		constraint order_client_pkey
			primary key,
	amount double precision not null,
	place_id bigint not null,
	payment_client_id bigint
		constraint fkd25skig85139vk5372c9ett8h
			references payment,
	payment_customer_id bigint
		constraint fkhmtgvlwn6mqj88aflf99ttehx
			references payment
)
;

alter table order_client owner to postgres
;

