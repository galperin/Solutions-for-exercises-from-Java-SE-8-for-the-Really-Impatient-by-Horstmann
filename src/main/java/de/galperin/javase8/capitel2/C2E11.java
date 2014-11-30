package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 29.10.14
 */
public class C2E11 implements Exercise {

    @Test
    @Override
    public void perform() {
        List<ArrayList<String>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList("01", "02", "03")));
        list.add(new ArrayList<>(Arrays.asList("04", "05")));
        list.add(new ArrayList<>(Arrays.asList("06", "07", "08", "09", "10")));
        assertEquals(10, collect(list.stream()).size());
    }


    public List<String> collect(Stream<ArrayList<String>> stream) {
        String[] array = stream.flatMap(Collection::stream).toArray(String[]::new);
        IntStream range = IntStream.range(0, array.length);
        List<String> result = new ArrayList<>(Arrays.asList(new String[array.length]));
        range.parallel().forEach(e -> result.set(e, array[e]));
        return result;
    }
}
