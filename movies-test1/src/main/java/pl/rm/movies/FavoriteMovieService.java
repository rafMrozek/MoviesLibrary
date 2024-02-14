package pl.rm.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteMovieService {

    private final FavoriteMovieRepository favoriteMovieRepository;

    @Autowired
    public FavoriteMovieService(FavoriteMovieRepository favoriteMovieRepository) {
        this.favoriteMovieRepository = favoriteMovieRepository;
    }

    public void addToFavorites(MovieEntity movieEntity) {
        favoriteMovieRepository.addToFavorites(movieEntity);
    }

    public boolean isFavorite(MovieEntity movieEntity) {
        return favoriteMovieRepository.isFavorite(movieEntity.getId());
    }

    public List<MovieEntity> getFavorites() {
        return favoriteMovieRepository.getFavorites();
    }
}
