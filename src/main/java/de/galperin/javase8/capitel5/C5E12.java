package de.galperin.javase8.capitel5;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 30.11.14
 */
public class C5E12 implements Exercise {

    @Test
    @Override
    public void perform() {
        assertFalse(alert(
                LocalTime.of(0, 40), ZoneId.of("America/Los_Angeles"),
                LocalTime.of(9, 50), ZoneId.of("Europe/Paris")));
        assertTrue(alert(
                LocalTime.of(1, 40), ZoneId.of("America/Los_Angeles"),
                LocalTime.of(9, 50), ZoneId.of("Europe/Paris")));
        assertFalse(alert(
                LocalTime.of(2, 40), ZoneId.of("America/Los_Angeles"),
                LocalTime.of(9, 50), ZoneId.of("Europe/Paris")));
    }

    public boolean alert(LocalTime appointmentTime,
                         ZoneId appointmentZone,
                         LocalTime localTime,
                         ZoneId localZone) {
        ZonedDateTime appointment = ZonedDateTime.of(
                LocalDate.now(),
                appointmentTime,
                appointmentZone
        );
        long duration = Duration.between(
                ZonedDateTime.of(LocalDate.now(), localTime, localZone).toInstant(),
                appointment.withZoneSameInstant(localZone).toInstant())
                .toMinutes();
        return 0 < duration && duration < 60;

    }
}
