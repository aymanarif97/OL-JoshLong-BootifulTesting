package com.ayman.contract.testing.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;



@RunWith(SpringRunner.class)
@SpringBootTest
// @AutoConfigureWireMock(port =8080) // Before Contract
@AutoConfigureStubRunner (
        ids="com.ayman.contract.testing.producer:+:8080",// + means latest version
        stubsMode=StubRunnerProperties.StubsMode.LOCAL// Local classpath. Not in .m2(CLASSPATH) or artifactory(REMOTE)
)
@AutoConfigureJson
public class ReservationClientTest {

    @Autowired
    private ReservationClient reservationClient;


    @Autowired
    private ObjectMapper objectMapper;// JACKSON: create instances of the records

    @Test
    public void getAllReservation() throws Exception{

        String json = this.objectMapper.writeValueAsString(Arrays.asList(
                new Reservation("1", "Ayman"),
                new Reservation("2", "Chinmay")
        ));

        WireMock
           .stubFor(WireMock.get(WireMock.urlEqualTo("/reservations"))
               .willReturn(WireMock.aResponse()
                                     .withBody(json)
                                     .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                     .withStatus(200)
               )
           );


        Flux<Reservation> response = this.reservationClient.getAllReservations(); // Publisher

        List<String> names = Arrays.asList("Ayman","Chinmay");

        StepVerifier
                .create(response)// Publisher
                .expectNextMatches(reservation -> names.contains(reservation.getReservationName()))
                .expectNextMatches(reservation -> names.contains(reservation.getReservationName()))
                .verifyComplete();
    }

}
