desc emaillist;

-- insert
insert
into emaillist
values (null, '둘', '리', 'dooly@gmail.com');

-- list
select id, first_name, last_name, email
from emaillist
order by id desc;

-- delete
delete
from emaillist
where id = 1;

select * from author;

delete from book;
delete from author;

select a.id, a.title, b.name, a.status from book a join author b on a.author_id = b.id;