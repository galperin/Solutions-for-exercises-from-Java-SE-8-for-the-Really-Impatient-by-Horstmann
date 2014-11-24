package de.galperin.javase8.capitel5;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 24.11.14
 */
public class C5E7 implements Exercise {

    class TimeInterval {
        LocalTime start;
        LocalTime end;

        TimeInterval(LocalTime start, LocalTime end) {
            if (Objects.isNull(start) || Objects.isNull(end) || start.isAfter(end)) {
                throw new IllegalArgumentException();
            }
            this.start = start;
            this.end = end;
        }

        boolean overlap(TimeInterval second) {
            return this.start.isAfter(second.start) && this.start.isBefore(second.end)
                    || second.start.isAfter(this.start) && second.start.isBefore(this.end);
        }
    }

    @Test
    @Override
    public void perform() {
        TimeInterval first = new TimeInterval(
                LocalTime.of(10, 0),
                LocalTime.of(12, 0));
        TimeInterval second = new TimeInterval(
                LocalTime.of(11, 30),
                LocalTime.of(14, 0));
        assertTrue(first.overlap(second));
        first = new TimeInterval(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0));
        assertFalse(first.overlap(second));
    }
}
