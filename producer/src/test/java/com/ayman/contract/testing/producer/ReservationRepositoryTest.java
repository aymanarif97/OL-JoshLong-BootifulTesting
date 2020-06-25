package com.ayman.contract.testing.producer;

import com.ayman.contract.testing.producer.ReservationRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testCustomQuery() throws Exception {
        // 1. Delete everything in DB
        // 2. Write 4 records
        // 3. Query by name and then return/assert count

        Flux<Reservation> reservationFlux = this.reservationRepository.deleteAll() //1.
                .thenMany(
                        Flux.just("A", "B", "C", "C") // 2.1. Create FLux
                        .map(name -> new Reservation(null, name)) // 2.2. Create Reservation objects for every Flux item
                        .flatMap(r -> this.reservationRepository.save(r)) // 2.3. Flatmap and save in database
                    )//2.
                .thenMany(this.reservationRepository.findByName("C"));//3. Query

        // Verifier results using StepVerifier

        StepVerifier
                .create(reservationFlux)
                .expectNextCount(2)
                .verifyComplete();
    }


}
