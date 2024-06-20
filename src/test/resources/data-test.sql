insert into place (name, city, address) values('Club 40', 'Moscow', 'Lenina 1');

insert into event (name, event_type_id, place_id, event_date) values(
    'Birthday Malezhik',
    (select id from event_type where name = 'theatre'),
    (select id from place where name = 'Club 40' limit 1),
    '2024-06-01'
);

insert into ticket (price, event_id) values (100, 1);