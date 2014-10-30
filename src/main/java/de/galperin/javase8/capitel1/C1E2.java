package de.galperin.javase8.capitel1;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * User: eugen
 * Date: 27.10.14
 */
public class C1E2 implements Exercise {

    @Test
    @Override
    public void perform() {
        Arrays.asList(getChildDirsWithLambda(".")).forEach(System.out::println);
        System.out.println("---------------------------------------------");
        Arrays.asList(getChildDirsWithMethodReference(".")).forEach(System.out::println);
    }

    private static File[] getChildDirsWithLambda(String dir) {
        File dirFile = new File(dir);
        return dirFile.listFiles(d -> d.isDirectory());
    }

    private static File[] getChildDirsWithMethodReference(String dir) {
        File dirFile = new File(dir);
        return dirFile.listFiles(File::isDirectory);
    }

}
