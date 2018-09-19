
insert into payment (id, token) values (1,'111110');
insert into payment (id, token) values (2,'11tr11');
insert into payment (id, token) values (3,'1a11112');
insert into payment (id, token) values (4,'111f13');
insert into payment (id, token) values (5,'1b1d14');
insert into payment (id, token) values (6,'111f15');
insert into payment (id, token) values (7,'1a1s16');
insert into payment (id, token) values (8,'11df117');
insert into payment (id, token) values (9,'111118');
insert into payment (id, token) values (10,'111119');

insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (1, 10, 1, 1, 1);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (2, 10, 2, 1, 1);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (3, 10, 3, 2, 6);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (4, 4.1, 4, 3, 4);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (5, 10, 5, 6, 8);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (6, 4.1, 6, 7, 4);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (7, 10.9, 7, 3, 3);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (8, 18, 8, 3, 2);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (9, 4.1, 9, 4, 4);
insert into order_client (id, amount, place_id, payment_client_id, payment_customer_id)values (10, 45, 10, 1, 2);