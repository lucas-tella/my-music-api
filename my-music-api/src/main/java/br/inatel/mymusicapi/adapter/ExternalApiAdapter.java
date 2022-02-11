package br.inatel.mymusicapi.adapter;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.mymusicapi.dto.TrackExtendedDto;
import reactor.core.publisher.Mono;

@Component
public class ExternalApiAdapter {
    @Value("${deezer.api.url}")
    private String url;
    @Value("${deezer.api.key}")
    private String key;
    public TrackExtendedDto getTrackById(String id) {
    	WebClient webClient = buildWebClient();
        URI trackLocation = UriComponentsBuilder.fromUriString(url)
                .path("/track")
                .path("/{id}")
                .build(id);
        Mono<TrackExtendedDto> trackMono = webClient.get()
                    .uri(trackLocation)
                    .header("x-rapidapi-key", key)
                    .retrieve()
                    .onStatus(HttpStatus::isError, this::throwException)
                    .bodyToMono(TrackExtendedDto.class);
        return trackMono.block();
    }
    @SuppressWarnings("unchecked")
    private Mono<Exception> throwException(ClientResponse response){
        Map<String, Object> responseBody = response.bodyToMono(Map.class).block();
        String errorMessage = (String) responseBody.get("Error");
        return Mono.error(new Exception(errorMessage));
    }
    private WebClient buildWebClient(){
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }
}