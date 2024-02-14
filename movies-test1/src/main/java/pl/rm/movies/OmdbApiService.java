package pl.rm.movies;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbApiService {

    private final WebClient webClient;



    public OmdbApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://www.omdbapi.com/")
                .build();
    }

    public Mono<Movie> searchByTitle(String title, String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("t", title)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(Movie.class);
    }

}
