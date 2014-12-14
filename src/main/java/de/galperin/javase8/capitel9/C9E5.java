package de.galperin.javase8.capitel9;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E5 {

    public static void main(String[] args) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(C9E5.class.getResource("/alice.txt").toURI()));
            byte[] reversedBytes = new byte[bytes.length];
            for (int i = 0, j = bytes.length - 1; i < bytes.length; i++, j--) {
                reversedBytes[i] = bytes[j];
            }
            Files.write(Paths.get(System.getProperty("java.io.tmpdir"), "alice.txt"), reversedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
