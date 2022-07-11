create table users (id  serial not null, login varchar(255), password varchar(255) not null, first_name varchar(255) not null, last_name varchar(255) not null, primary key (id));
create table messages (id  serial not null, text TEXT not null, time timestamp not null, user_id int4 not null, primary key (id), FOREIGN KEY (user_id) REFERENCES users(id));
