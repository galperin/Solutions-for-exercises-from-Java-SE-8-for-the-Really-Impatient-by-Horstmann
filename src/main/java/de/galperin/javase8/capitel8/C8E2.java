package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

/**
 * User: eugen
 * Date: 06.12.14
 */
public class C8E2 implements Exercise {

    @Test(expected = Exception.class)
    @Override
    public void perform() {
        java.lang.Math.negateExact(Integer.MIN_VALUE);
    }
}
