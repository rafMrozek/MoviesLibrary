package pl.rm.moviestest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class MovieController {

    private final OmdbApiService omdbApiService;
    private final MovieRepository movieRepository;

    private final FavoriteMovieService favoriteMovieService;

    @Value("${omdb.apiKey}")
    private String omdbApiKey;


    @Autowired
    public MovieController(OmdbApiService omdbApiService, MovieRepository movieRepository, FavoriteMovieService favoriteMovieService) {
        this.omdbApiService = omdbApiService;
        this.movieRepository = movieRepository;
        this.favoriteMovieService = favoriteMovieService;
    }

    @GetMapping("/search")
    public Mono<MovieEntity> searchMovieByTitle(@RequestParam String title) {
        return omdbApiService.searchByTitle(title, omdbApiKey)
                .map(movie -> {
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setMovieTitle(movie.getTitle());
                    movieEntity.setReleaseYear(movie.getYear());

                    movieEntity.setFavorite(favoriteMovieService.isFavorite(movieEntity));

                    movieRepository.save(movieEntity);

                    return movieEntity;
                });
    }
    @PostMapping("/favorite")
    public Mono<String> addToFavorites(@RequestParam Long movieId) {   //Long movieId

        MovieEntity movieEntity = movieRepository.findById(movieId).orElse(null); //movieId

        if (movieEntity != null) {

            favoriteMovieService.addToFavorites(movieEntity);
            return Mono.just("Film dodano do ulubionych.");
        } else {
            return Mono.just("Film o podanym ID nie istnieje.");
        }
    }
    @GetMapping("/favorites")
    public Mono<List<MovieEntity>> getFavorites() {
        List<MovieEntity> favorites = favoriteMovieService.getFavorites();
        return Mono.just(favorites);
    }
}

