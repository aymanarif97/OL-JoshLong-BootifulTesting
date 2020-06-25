package com.ayman.contract.testing.consumer;

// DTO

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    // Producer: id, name
    // Consumer: id, reservationName
    // Mismatch: Need to establish a contract between producer and consumer
    private String id, reservationName;
}
