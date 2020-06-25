package com.ayman.contract.testing.producer;


import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

@Import(ReservationHTTPConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = "server.port=8080",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BaseClass {

    @MockBean
    private ReservationRepository reservationRepository;

    @LocalServerPort
    private int port;

    @Before
    public void before() throws Exception {
        Mockito.when(this.reservationRepository.findAll())
                .thenReturn(Flux.just(new Reservation("1", "Ayman"),
                                      new Reservation("2", "Chinmay")));
        RestAssured.baseURI = "http://localhost:" + this.port;
    }

}
