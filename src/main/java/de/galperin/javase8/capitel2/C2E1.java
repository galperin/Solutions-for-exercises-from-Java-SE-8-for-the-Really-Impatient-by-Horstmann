package de.galperin.javase8.capitel2;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 28.10.14
 */
public class C2E1 implements Exercise {

    @Test
    @Override
    public void perform() {
        List<String> words = getWordsAsList();
        assertEquals(words.stream().filter(s -> s.length() > 12).count(), countConcurrentWithoutStreams(words));
    }

    private static long countConcurrentWithoutStreams(List<String> words) {
        try {
            int cores = Runtime.getRuntime().availableProcessors();
            int chunkSize = words.size() / cores;
            List<List<String>> chunks = new LinkedList<>();
            for (int i = 0; i < words.size(); i += chunkSize) {
                chunks.add(words.subList(i, i + Math.min(chunkSize, words.size() - i)));
            }
            ExecutorService pool = Executors.newFixedThreadPool(cores);
            Set<Future<Long>> set = new HashSet<>();
            for (List<String> strings : chunks) {
                Callable<Long> callable = () -> {
                    long chunkCount = 0;
                    for (String string : strings) {
                        if (string.length() > 12) chunkCount++;
                    }
                    return chunkCount;
                };
                Future<Long> future = pool.submit(callable);
                set.add(future);
            }
            long count = 0;
            for (Future<Long> future : set) {
                count += future.get();
            }
            pool.shutdown();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
