package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E9 implements Exercise {

    @Test
    @Override
    public void perform() {
        Collection2<Integer> c = new ArrayList2<>();
        c.add(100);
        c.add(-5);
        CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();
        c.forEachIf(set::add, e -> e > 0);
        assertEquals(1, set.size());
        assertEquals(100, set.toArray()[0]);
    }
}

interface Collection2<T> extends Collection<T> {
    default void forEachIf(Consumer<T> action, Predicate<T> filter) {
        forEach(e -> {
            if (filter.test(e)) {
                action.accept(e);
            }
        });
    }
}

class ArrayList2<T> extends ArrayList<T> implements Collection2<T> {
}
