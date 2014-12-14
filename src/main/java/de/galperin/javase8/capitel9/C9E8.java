package de.galperin.javase8.capitel9;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E8 implements Exercise {

    @Test
    @Override
    public void perform() {
        Point a = new Point(Integer.MAX_VALUE, 8);
        Point b = new Point(-100, 8);
        assertTrue(a.compareTo(b) > 0);
        Point c = new Point(-Integer.MAX_VALUE, 8);
        Point d = new Point(100, 8);
        assertTrue(c.compareTo(d) < 0);
    }

    class Point implements Comparable<Point> {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point other) {
            int diff;
            try {
                diff = Math.subtractExact(x, other.x);
            } catch (ArithmeticException e) {
                return x;
            }
            if (diff != 0) return diff;
            try {
                return Math.subtractExact(y, other.y);
            } catch (ArithmeticException e) {
                return y;
            }
        }
    }
}
