package pl.rm.moviestest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MovieController {

    private final OmdbApiService omdbApiService;
    private final MovieRepository movieRepository;

    @Value("${omdb.apiKey}")
    private String omdbApiKey;

    @Autowired
    public MovieController(OmdbApiService omdbApiService, MovieRepository movieRepository) {
        this.omdbApiService = omdbApiService;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/search")
    public Mono<MovieEntity> searchMovieByTitle(@RequestParam String title) {
        return omdbApiService.searchByTitle(title, omdbApiKey)
                .map(movie -> {
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setMovieTitle(movie.getTitle());
                    movieEntity.setReleaseYear(movie.getYear());

                    movieRepository.save(movieEntity);

                    return movieEntity;
                });
    }
}

