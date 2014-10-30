package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * User: eugen
 * Date: 28.10.14
 */
public class C2E6 implements Exercise {

    @Test
    @Override
    public void perform() {
        characterStream("abcdef").forEach(System.out::println);
    }

    public Stream<Character> characterStream(String string) {
        return IntStream.range(0, string.length()).mapToObj(string::charAt);
    }
}
