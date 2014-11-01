package de.galperin.javase8.capitel3;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: eugen
 * Date: 01.11.14
 */
public class C3E1 implements Exercise {

    @Test
    @Override
    public void perform() {
        Logger.getGlobal().setLevel(Level.OFF);
        logIf(Level.INFO, () -> true, () -> "you'll never see it");
        Logger.getGlobal().setLevel(Level.ALL);
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < a.length; i++) {
            log(i, a);
        }
    }

    private void log(int i, int[] a) {
        logIf(Level.INFO, () -> i == 10, () -> "a[10] = " + a[10]);
    }

    public void logIf(Level level, Supplier<Boolean> condition, Supplier<String> message) {
        Logger logger = Logger.getGlobal();
        if (logger.isLoggable(level)   //evaluate condition only if the logger will log the message
                && condition.get()) {
            logger.log(level, message.get());
        }
    }
}
