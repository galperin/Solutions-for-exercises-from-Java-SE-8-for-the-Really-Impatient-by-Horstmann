package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E1 implements Exercise {

    @Test
    @Override
    public void perform() {
        final long currentThreadId = Thread.currentThread().getId();
        String[] array = getWordsAsArray();
        //sort
        CopyOnWriteArraySet<Long> threadIds = new CopyOnWriteArraySet<>();
        Arrays.sort(array, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });
        assertEquals(1, threadIds.size());
        assertEquals(currentThreadId, threadIds.toArray()[0]);
        //parallelSort
        threadIds.clear();
        Arrays.parallelSort(array, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });
        assertTrue(threadIds.size() > 1);
    }

}
