package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E4 implements Exercise {

    @Test
    @Override
    public void perform() {
        File[] files = sort(".");
        Arrays.asList(files).forEach(System.out::println);
    }

    private static File[] sort(String dir){
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        Arrays.sort(files, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else {
                return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
            }
        });
        return files;
    }
}
