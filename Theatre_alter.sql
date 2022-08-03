ALTER TABLE customer
    ADD CONSTRAINT fk_movie_id FOREIGN KEY (movie_id) REFERENCES movie(movie_id);

ALTER TABLE movie
    ADD CONSTRAINT fk_customer FOREIGN KEY (customer) REFERENCES customer(customer_id);
