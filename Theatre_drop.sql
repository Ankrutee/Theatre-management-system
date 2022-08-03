ALTER TABLE customer
    DROP FOREIGN KEY fk_movie_id;

ALTER TABLE customer
    DROP FOREIGN KEY fk_customer;

DROP TABLE movie;
DROP TABLE TheatreStaff;
DROP TABLE customer;
DROP TABLE super_admin;

DROP DATABASE Theatre;
