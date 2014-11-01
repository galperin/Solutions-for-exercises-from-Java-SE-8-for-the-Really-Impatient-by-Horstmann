package de.galperin.javase8.capitel3;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.function.BooleanSupplier;

/**
 * User: eugen
 * Date: 01.11.14
 */
public class C3E3 implements Exercise {

    @Test(expected = AssertionError.class)
    @Override
    public void perform() {
        assertThat(() -> 2 * 2 == 5);
    }

    public void assertThat(BooleanSupplier assertion) {
        if (!assertion.getAsBoolean()) {
            throw new AssertionError();
        }
    }
}
