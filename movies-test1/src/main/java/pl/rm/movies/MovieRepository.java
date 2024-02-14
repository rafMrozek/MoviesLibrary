package pl.rm.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MovieEntity saveAndGetByTitle(MovieEntity movieEntity) {
        String sql = "INSERT INTO movie (title, release_year) VALUES (?, ?)";
        jdbcTemplate.update(sql, movieEntity.getMovieTitle(), movieEntity.getReleaseYear());

        return movieEntity;
    }

    public Optional<MovieEntity> findByTitle(String title) {
        String sql = "SELECT * FROM movie WHERE title = ?";
        try {
            MovieEntity movie = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MovieEntity.class), title);
            return Optional.ofNullable(movie);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    public Optional<MovieEntity> findByTitleAndId(String title, Long id) {
        String sql = "SELECT * FROM movie WHERE title = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MovieEntity.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
