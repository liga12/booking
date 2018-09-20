insert into organization (id, customer_id, email, location, name, phone, visible)
values (1, '6', 'e','l','name4546', '11134',true );

insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('CINEMA', 1, '2a', 1, 'd1', 'loc1', 'name2', 'url1', 'CINEMA', true ,1);
 insert into age_constrain_event (min_age, id) values (12,1);
 insert into cinema_event (id) values (1);

insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('CINEMA', 2, '2a', 1, 'd1', 'loc1', 'name2', 'url1', 'CINEMA', false ,1);
 insert into age_constrain_event (min_age, id) values (12,2);
 insert into cinema_event (id) values (2);