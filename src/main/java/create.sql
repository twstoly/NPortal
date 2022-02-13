SET MODE postgres

CREATE TABLE IF NOT EXISTS departments(
    id SERIAL PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    numberOfEmployees INTEGER
)

CREATE TABLE IF NOT EXISTS news(
    id SERIAL PRIMARY KEY auto_increment,
    content VARCHAR,
    author VARCHAR,
    departmentId INTEGER,
     type VARCHAR
)

CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY auto_increment,
    name VARCHAR,
    role VARCHAR,
    departmentId INTEGER
)
CREATE DATABASE call_api_test WITH TEMPLATE call_api;



















































