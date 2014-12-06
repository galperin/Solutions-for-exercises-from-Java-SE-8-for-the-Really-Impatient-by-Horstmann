package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: eugen
 * Date: 06.12.14
 */
public class C8E1 implements Exercise {

    @Test
    @Override
    public void perform() {
        int n1 = Integer.MAX_VALUE;
        int n2 = 100;
        int n3 = n1 + n2;
        assertNotEquals("2147483747", n3 + "");
        assertEquals(2147483747L, Integer.toUnsignedLong(n3));
        assertFalse(n3 > n2);
        assertTrue(Integer.compareUnsigned(n3, n2) > 0);
        int n4 = n3 / n2;
        assertNotEquals(21474837, n4);
        assertNotEquals(21474837, Integer.toUnsignedLong(n4));
        assertEquals(21474837, Integer.divideUnsigned(n3, n2));
    }
}
