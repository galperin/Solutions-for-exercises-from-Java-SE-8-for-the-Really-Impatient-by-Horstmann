package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * User: eugen
 * Date: 30.10.14
 */
public class C2E13 implements Exercise {

    @Test
    @Override
    public void perform() {
        List<String> words = getWordsAsList();
        words.parallelStream()
                .filter(w -> w.length() < 12)
                .collect(groupingBy(String::length, counting()))
                .forEach((k, v) -> System.out.printf("%d - %d\n", k, v));
    }
}
