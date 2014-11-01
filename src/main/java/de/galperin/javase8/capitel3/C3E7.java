package de.galperin.javase8.capitel3;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;

import static de.galperin.javase8.capitel3.CompareOptions.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * User: eugen
 * Date: 01.11.14
 */
public class C3E7 implements Exercise {

    @Test
    @Override
    public void perform() {
        String[] values = {
                "BBB",
                " ccc",
                "aaa"
        };
        Arrays.sort(values, comparatorGenerator(EnumSet.of(
                REVERSE,
                CASE_INSENSITIVE,
                SPACE_INSENSITIVE
        )));
        assertArrayEquals(values, new String[]{
                " ccc",
                "BBB",
                "aaa"
        });
        Arrays.sort(values, comparatorGenerator(EnumSet.noneOf(CompareOptions.class)));
        assertArrayEquals(values, new String[]{
                " ccc",
                "BBB",
                "aaa"
        });
    }

    public Comparator<String> comparatorGenerator(EnumSet<CompareOptions> options) {
        return (x, y) -> {
            if (options.contains(CASE_INSENSITIVE)) {
                x = x.toLowerCase();
                y = y.toLowerCase();
            }
            if (options.contains(SPACE_INSENSITIVE)) {
                x = x.replaceAll("\\s+", "");
                y = y.replaceAll("\\s+", "");
            }
            return options.contains(REVERSE) ? y.compareTo(x) : x.compareTo(y);
        };
    }

}

enum CompareOptions {
    REVERSE,
    CASE_INSENSITIVE,
    SPACE_INSENSITIVE
}

