package com.ayman.contract.testing.producer;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.assertj.core.api.Assertions;
public class ReservationPojoTest {

    // create object
    @Test
    public void create() throws Exception{
        Reservation reservation = new Reservation("1","Ayman");
        Assert.assertEquals("Ayman", reservation.getName());// (expected, actual)
        Assert.assertThat(reservation.getName(),Matchers.equalToIgnoringCase("ayman"));// (actual, matcher)
        Assert.assertThat(reservation.getName(), new BaseMatcher() {
            @Override
            public boolean matches(Object item) {
                return Character.isUpperCase(((String) item).charAt(0));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Name should be valid uppercase.");
            }
        }); //(actual, matcher)

        Assertions.assertThat(reservation.getName().equalsIgnoreCase("ayman"));


    }
}
