package com.ayman.contract.testing.consumer;

// Client HTTP API

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ReservationClient {

    private final WebClient client;

    public Flux<Reservation> getAllReservations(){
        return this.client
                .get()
                .uri("http://localhost:8080/reservations")// from server
                .retrieve()
                .bodyToFlux(Reservation.class); // Convert HTTP content body to Flux of type Reservation

    }

}
