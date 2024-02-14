CREATE TABLE IF NOT EXISTS movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_year VARCHAR(4)
);
CREATE TABLE IF NOT EXISTS favorite_movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT,
    favorite BOOLEAN,
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);
