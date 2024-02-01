package pl.rm.moviestest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteMovieRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoriteMovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToFavorites(MovieEntity movieEntity) {
        String sql = "INSERT INTO favorite_movie (movie_id) VALUES (?)";
        jdbcTemplate.update(sql, movieEntity.getId());
    }

    public boolean isFavorite(Long movieId) {
        String sql = "SELECT COUNT(*) FROM favorite_movie WHERE movie_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, movieId) > 0;
    }

    public List<MovieEntity> getFavorites() {
        String sql = "SELECT m.id, m.title, m.release_year FROM movie m JOIN favorite_movie f ON m.id = f.movie_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MovieEntity.class));
    }
}
