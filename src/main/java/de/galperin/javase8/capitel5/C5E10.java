package de.galperin.javase8.capitel5;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 28.11.14
 */
public class C5E10 implements Exercise {

    @Test
    @Override
    public void perform() {
        LocalTime arrival = getArrivalTime(
                LocalTime.of(15, 5),
                ZoneId.of("America/Los_Angeles"),
                Duration.ofMinutes(10 * 60 + 50),
                ZoneId.of("Europe/Paris")
        );
        assertEquals(10, arrival.getHour());
        assertEquals(55, arrival.getMinute());
    }

    public LocalTime getArrivalTime(LocalTime departure,
                                    ZoneId location,
                                    Duration duration,
                                    ZoneId destination) {
        ZonedDateTime departureDateTime = ZonedDateTime.of(
                LocalDate.now(),
                departure,
                location
        );
        ZonedDateTime arrivalDateTime = departureDateTime.plus(duration);
        return arrivalDateTime.withZoneSameInstant(destination).toLocalTime();
    }
}
