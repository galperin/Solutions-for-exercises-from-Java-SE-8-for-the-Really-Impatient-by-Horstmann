package de.galperin.javase8.capitel9;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E10 implements Exercise {

    @Test
    @Override
    public void perform() {
        LabeledPoint a = new LabeledPoint(null, 0, 0);
        LabeledPoint b = new LabeledPoint(null, 0, 0);
        LabeledPoint c = new LabeledPoint("a", 0, 0);
        assertTrue(a.compareTo(b) == 0);
        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(b) > 0);
    }

    class LabeledPoint implements Comparable<LabeledPoint> {

        String label;
        int x;
        int y;

        public LabeledPoint(String label, int x, int y) {
            this.label = label;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(LabeledPoint other) {
            int diff = Integer.compare(x, other.x);
            if (diff != 0) return diff;
            diff = Integer.compare(y, other.y);
            if (diff != 0) return diff;
            return Objects.compare(label, other.label, (a, b) -> {
                if(a == null) return -1;
                if(b == null) return 1;
                return a.compareTo(b);
            });
        }
    }
}
