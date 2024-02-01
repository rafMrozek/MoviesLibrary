package pl.rm.moviestest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(MovieEntity movieEntity) {
        String sql = "INSERT INTO movie (title, realase_year) VALUES (?, ?)";
        jdbcTemplate.update(sql, movieEntity.getMovieTitle(), movieEntity.getReleaseYear());
    }

    public List<MovieEntity> findByTitle(String title) {
        String sql = "SELECT * FROM movie WHERE title = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MovieEntity.class), title);
    }
    public Optional<MovieEntity> findById(Long id) {
        String sql = "SELECT * FROM movie WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MovieEntity.class), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
