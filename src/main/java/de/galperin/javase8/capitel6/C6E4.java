package de.galperin.javase8.capitel6;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 01.12.14
 */
public class C6E4 implements Exercise {

    @Test
    @Override
    public void perform() {
        LongAccumulator maxAccumulator = new LongAccumulator(Math::max, 0);
        LongAccumulator minAccumulator = new LongAccumulator(Math::min, 10000);
        IntStream.range(0, 10).forEach(i ->
                new Thread(() ->
                        Stream.generate(Math::random).map(d ->
                                Double.valueOf(d * 10000).intValue()).limit(100).forEach(e -> {
                            maxAccumulator.accumulate(e);
                            minAccumulator.accumulate(e);
                        })).start());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(maxAccumulator.intValue() > 0);
        assertTrue(minAccumulator.intValue() < 10000);
    }
}
