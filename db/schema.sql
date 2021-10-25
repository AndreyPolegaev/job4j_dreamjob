CREATE TABLE post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE candidates
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);


CREATE TABLE users
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
);

select *from users;