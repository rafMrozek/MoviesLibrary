package pl.rm.moviestest1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MovieController {

    private final OmdbApiService omdbApiService;

    @Value("${omdb.apiKey}")
    private String omdbApiKey;

    public MovieController(OmdbApiService omdbApiService) {
        this.omdbApiService = omdbApiService;
    }

    @GetMapping("/search")
    public Mono<MovieEntity> searchMovieByTitle(@RequestParam String title) {
        Mono<MovieEntity> movieEntityMono = omdbApiService.searchByTitle(title, omdbApiKey)
                .map(movie -> {
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setMovieTitle(movie.getTitle());
                    movieEntity.setReleaseYear(movie.getYear());
                    return movieEntity;
                });

        movieEntityMono.subscribe(movieEntity -> {
            System.out.println("Title: " + movieEntity.getMovieTitle());
            System.out.println("Year: " + movieEntity.getReleaseYear());
        });

        return movieEntityMono;
    }
}

