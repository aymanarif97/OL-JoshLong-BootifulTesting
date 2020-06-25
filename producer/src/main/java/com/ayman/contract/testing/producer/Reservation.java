package com.ayman.contract.testing.producer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    private String myKey;
    private String name;

    /*
    Using lombok instead
    public Reservation(String s, String ayman) {
    }
    */



}
