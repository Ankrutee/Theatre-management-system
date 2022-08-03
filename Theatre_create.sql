CREATE DATABASE Theatre;
USE Theatre;

GRANT ALL PRIVILEGES ON Theatre.* TO 'ankrutee'@'localhost';

CREATE TABLE customer (
    customer_id VARCHAR(100) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    movie_id VARCHAR(100),
    CONSTRAINT pk_customer PRIMARY KEY (customer_id)
);

CREATE TABLE movie (
    movie_id VARCHAR(100) NOT NULL,
    movie_name VARCHAR(100) NOT NULL,
    customer VARCHAR(100),
    CONSTRAINT pk_movie PRIMARY KEY (movie_id)
);

CREATE TABLE TheatreStaff (
    staff_id VARCHAR(100) NOT NULL,
    staff_name VARCHAR(100) NOT NULL,
    staff_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_staff PRIMARY KEY (staff_id)
);

CREATE TABLE super_admin (
    super_admin_id VARCHAR(100) NOT NULL,
    super_admin_name VARCHAR(100) NOT NULL,
    super_admin_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_super_admin PRIMARY KEY (super_admin_id)
);
