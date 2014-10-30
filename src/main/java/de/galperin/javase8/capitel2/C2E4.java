package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * User: eugen
 * Date: 28.10.14
 */
public class C2E4 implements Exercise {

    @Test
    @Override
    public void perform() {
        int[] values = {1, 4, 9, 16};
        IntStream streamOfInts = Arrays.stream(values);
        streamOfInts.forEach(System.out::println);
    }
}
