package com.ayman.contract.testing.producer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(ReservationHTTPConfiguration.class)
@RunWith(SpringRunner.class)
public class ReservationHTTPTest {

    // Mock = Crate ojects with defualt values: 0, null, false
    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllReservationFromAPI() throws Exception{

        // Stub : Mock with values
        // ACTUAL
        Mockito.when(this.reservationRepository.findAll())
                .thenReturn(Flux.just(new Reservation("1", "Ayman"),
                                      new Reservation("2", "Chinmay")));

        this.webTestClient
                .get()
                .uri("/reservations")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                    .jsonPath("@[0].name").isEqualTo("Ayman")
                    .jsonPath("@[1].name").isEqualTo("Chinmay");


    }

}
