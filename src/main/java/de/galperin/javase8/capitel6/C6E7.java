package de.galperin.javase8.capitel6;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 03.12.14
 */
public class C6E7 implements Exercise {

    @Test
    @Override
    public void perform() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        getWordsAsList().stream().forEach(w -> map.computeIfAbsent(w, k -> (long) k.length()));
        Map.Entry<String, Long> entry = map.reduceEntries(8, (v1, v2) ->
                v1.getValue() > v2.getValue() ? v1 : v2);
        assertEquals("unenforceability", entry.getKey());
    }
}
