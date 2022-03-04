package br.inatel.mymusicapi.adapter;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.mymusicapi.dto.TrackExtendedDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class ExternalApiAdapter {
    @Value("${deezer.api.url}")
    private String url;
    @Value("${deezer.api.key}")
    private String key;
    public TrackExtendedDto getTrackById(String id) {
    	WebClient webClient = buildWebClient();
        Mono<TrackExtendedDto> trackMono = webClient.get()
                .uri("/track/{id}", id)
                .header("x-rapidapi-key", key)
                .retrieve()
                .onStatus(HttpStatus::isError, this::throwException)
                .bodyToMono(TrackExtendedDto.class);
        log.info("Track data retrieved from external api.");
        return trackMono.block();
    }
    @SuppressWarnings("unchecked")
    private Mono<Exception> throwException(ClientResponse response){
    	log.info("External api error.");
        Map<String, Object> responseBody = response.bodyToMono(Map.class).block();
        String errorMessage = (String) responseBody.get("An error has occurred while trying to reach external API.");
        return Mono.error(new Exception(errorMessage));
    }
    private WebClient buildWebClient(){
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }
}