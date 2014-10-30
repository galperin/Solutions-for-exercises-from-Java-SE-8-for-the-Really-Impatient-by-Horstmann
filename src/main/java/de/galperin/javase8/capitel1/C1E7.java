package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E7 implements Exercise {

    @Test
    @Override
    public void perform() {
        new Thread(
                andThen(
                        () -> System.out.println("First"),
                        () -> System.out.println("Second")
                )).start();
    }

    public static Runnable andThen(Runnable first, Runnable second) {
        return () -> {
            first.run();
            second.run();
        };
    }
}
