CREATE TABLE if not exists post
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    time timestamp
);

CREATE TABLE if not exists cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists candidates
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    time    timestamp,
    city_fk int references cities(id)
);

CREATE TABLE if not exists users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT,
    password TEXT
);

insert into cities (name) values
('Moscow'),
('New York'),
('London'),
('Berlin'),
('Parish');
