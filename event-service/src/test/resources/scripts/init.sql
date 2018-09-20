insert into organization (id, customer_id, email, location, name, phone, visible)
values (1, '1', 'email12','loc11','name', '1113',true );

insert into organization (id, customer_id, email, location, name, phone, visible)
values (2, '2', 'email1','loc1234','name45', '1131',true );

insert into organization (id, customer_id, email, location, name, phone, visible)
values (3, '3', 'email4','loc1rewger','n45ame', '1151',true );

insert into organization (id, customer_id, email, location, name, phone, visible)
values (4, '4', 'email56','loc14535','namre', '1131',true );

insert into organization (id, customer_id, email, location, name, phone, visible)
values (5, '5', 'email22341','loc1435','nertame', '11134',false );

insert into organization (id, customer_id, email, location, name, phone, visible)
values (6, '6', 'e','l','name4546', '11134',false );



insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('CINEMA', 1, '2a', 1, 'd1', 'loc1', 'name2', 'url1', 'CINEMA', true ,1);
 insert into age_constrain_event (min_age, id) values (12,1);
 insert into cinema_event (id) values (1);

 insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('CINEMA', 2, 'a2', 2, 'c2', 'loc2', 'name21', 'url1', 'CINEMA', true ,1);
 insert into age_constrain_event (min_age, id) values (13,2);

  insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('CINEMA', 3, 'a', 3, 'd2', '2loc', 'name214', 'url1', 'CINEMA', true ,2);
 insert into age_constrain_event (min_age, id) values (13,3);
 insert into cinema_event (id) values (3);

   insert into abstract_event
 (type_discrimination,id, artists, date, description, location, name, photo_url, type, visible, organization_id)
 values ('THEATRE', 4, 'a', 4, 'd1', 'loc1', 'nr6amee214', 'url1', 'THEATRE',false ,3);
 insert into age_constrain_event (min_age, id) values (14,4);
 insert into cinema_event (id) values (4);



 insert into place (id, number, price, row, section_type, status, event_id)
 values (1, 1, 105, 15, 'FIRST_ROW','ACTIVE', 1);
 insert into place (id, number, price, row, section_type, status, event_id)
 values (2, 12, 160, 41, 'FIRST_ROW','ACTIVE', 1);
 insert into place (id, number, price, row, section_type, status, event_id)
 values (3, 4, 150, 51, 'MIDDLE_ROW','BYU', 2);
 insert into place (id, number, price, row, section_type, status, event_id)
 values (4, 16, 150, 41, 'MIDDLE_ROW','ACTIVE', 2);


