package com.ayman.contract.testing.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest // TOREAD: Test slices
public class ReservationEntityTest {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void create() throws Exception {
        Reservation reservation = new Reservation(null, "Aryaman");

        Mono<Reservation> saveToDatabase = this.reactiveMongoTemplate.save(reservation); // Mono is a publisher

        StepVerifier
                .create(saveToDatabase)
                .expectNextMatches(reservation2 -> reservation2.getMyKey() !=null && reservation2.getName().equalsIgnoreCase("aryaman")) //getId is randomly generated on save()
                .verifyComplete();

    }

}
