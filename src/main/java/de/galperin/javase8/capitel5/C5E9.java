package de.galperin.javase8.capitel5;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;

/**
 * User: eugen
 * Date: 27.11.14
 */
public class C5E9 implements Exercise {

    @Test
    @Override
    public void perform() {
        Instant now = Instant.now();
        ZoneId.getAvailableZoneIds().stream()
                .map(id -> now.atZone(ZoneId.of(id)).getOffset())
                .filter(o -> o.getTotalSeconds() % 3600 != 0)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
}
