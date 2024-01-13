package pl.rm.moviestest1;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbApiService {

    private final WebClient webClient;

    public OmdbApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://www.omdbapi.com/") // Ustaw bazowy adres API OMdb
                .build();
    }

    public Mono<String> searchByTitle(String title, String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("t", title)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
