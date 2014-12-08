package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 08.12.14
 */
public class C8E5 implements Exercise {

    @Test
    @Override
    public void perform() {
        ArrayList<String> words = new ArrayList<>(getWordsAsList());
        words.removeIf(w -> w.length() <= 12);
        assertEquals(33, words.size());
    }
}
