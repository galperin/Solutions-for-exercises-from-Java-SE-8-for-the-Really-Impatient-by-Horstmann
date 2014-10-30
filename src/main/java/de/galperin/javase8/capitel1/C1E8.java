package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E8 implements Exercise {

    @Test
    @Override
    public void perform() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for (String name : names) {
            runners.add(() -> System.out.println(name));
        }
        for (Runnable runner : runners) {
            new Thread(runner).start();
        }

        runners.clear();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            runners.add(() -> System.out.println(name));
        }
        for (Runnable runner : runners) {
            new Thread(runner).start();
        }
    }
}
