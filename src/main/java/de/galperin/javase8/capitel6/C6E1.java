package de.galperin.javase8.capitel6;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * User: eugen
 * Date: 30.11.14
 */
public class C6E1 implements Exercise {

    @Test
    @Override
    public void perform() {
        AtomicReference<String> longest = new AtomicReference<>();
        LongAccumulator accumulator = new LongAccumulator(Math::max, 0);
        List<String> words = getWordsAsList();
        words.parallelStream().forEach(
                next -> longest.updateAndGet(
                        current -> {
                            String result = next.length() > accumulator.intValue() ? next : current;
                            accumulator.accumulate(next.length());
                            return result;
                        }));
        System.out.println(longest.get());
    }
}
