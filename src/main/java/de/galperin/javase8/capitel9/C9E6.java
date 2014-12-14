package de.galperin.javase8.capitel9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E6 {

    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(C9E6.class.getResource("/alice.txt").toURI()));
            Collections.reverse(lines);
            Files.write(Paths.get(System.getProperty("java.io.tmpdir"), "alice.txt"), lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
