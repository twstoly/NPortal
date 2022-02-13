SET MODE postgres

CREATE TABLE IF NOT EXISTS department(
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    numberOfEmployees INTEGER
)

CREATE TABLE IF NOT EXISTS news(
    id int PRIMARY KEY auto_increment,
    content VARCHAR,
    author VARCHAR,
    numberOfEmployees INTEGER
)

CREATE TABLE IF NOT EXISTS users(
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    role VARCHAR,
    departmentId INTEGER
)




















































