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
    public Mono<Movie> searchMovieByTitle(@RequestParam String title) {
        Mono<Movie> movieMono = omdbApiService.searchByTitle(title, omdbApiKey);

    movieMono.subscribe(movie -> {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Year: " + movie.getYear());
    });

        return movieMono;
    }
}
