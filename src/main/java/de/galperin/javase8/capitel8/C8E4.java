package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 07.12.14
 */
public class C8E4 implements Exercise {

    final long m = 25214903917L;
    final long v = 246154705703781L;
    final long a = 11;
    final long n = 281474976710656L;

    @Test
    @Override
    public void perform() {
        long seed = Stream.iterate(prev(0), this::prev)
                .limit(1_000_000).min((x, y) ->
                        Long.compare(x ^ m, y ^ m)).get();
        Random generator = new Random(seed ^ m);
        long index = 0;
        double d;
        do {
            index++;
            d = generator.nextDouble();
        } while (d != 0);
        assertEquals(376050, index);
    }


    long prev(long s) {
        return (BigInteger.valueOf(s).subtract(BigInteger.valueOf(a)))
                .multiply(BigInteger.valueOf(v))
                .mod(BigInteger.valueOf(n)).longValue();
    }

}
