package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * User: eugen
 * Date: 28.10.14
 */
public class C2E5 implements Exercise {

    @Test
    @Override
    public void perform() {
        randomStream(System.currentTimeMillis(), 25214903917l, 11, (long) Math.pow(2, 48))
                .limit(10)
                .forEach(System.out::println);
    }

    public Stream<Long> randomStream(long seed, long a, long c, long m){
        return Stream.iterate(seed, e -> (a * e + c) % m);
    }
}
