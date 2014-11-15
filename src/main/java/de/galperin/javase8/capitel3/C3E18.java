package de.galperin.javase8.capitel3;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * User: eugen
 * Date: 15.11.14
 */
public class C3E18 implements Exercise {

    @FunctionalInterface
    public interface FunctionThatThrows<T, R> {
        R apply(T t) throws Exception;
    }

    @Override
    @Test(expected = RuntimeException.class)
    public void perform() {
        unchecked((String p) -> Files.readAllBytes(Paths.get(p)).length).apply("/dummy.txt");
    }

    public <T, R> Function<T, R> unchecked(FunctionThatThrows<T, R> f) {
        return (a) -> {
            try {
                return f.apply(a);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
