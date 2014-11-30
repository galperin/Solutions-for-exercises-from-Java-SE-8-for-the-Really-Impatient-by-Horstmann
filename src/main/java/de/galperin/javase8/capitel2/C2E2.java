package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.List;

/**
 * User: eugen
 * Date: 28.10.14
 */
public class C2E2 implements Exercise {

    @Test
    @Override
    public void perform() {
        List<String> words = getWordsAsList();
        words.stream().filter(s -> s.length() > 12)
                .limit(5).forEach(System.out::println);
    }
}

