CREATE TABLE IF NOT EXISTS post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS candidates
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
);

-- drop table users;
-- drop table candidates;
-- drop table post;
-- select *from post;
-- select *from users;
-- delete from post;