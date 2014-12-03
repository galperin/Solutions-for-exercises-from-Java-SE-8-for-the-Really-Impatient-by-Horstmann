package de.galperin.javase8.capitel6;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: eugen
 * Date: 02.12.14
 */
public class C6E6 implements Exercise {

    @Test
    @Override
    public void perform() {
        ConcurrentHashMap<String, Set<File>> map = new ConcurrentHashMap<>();
        File[] files = {
                new File(this.getClass().getResource("/alice.txt").getFile()),
                new File(this.getClass().getResource("/person.fxml").getFile()),
        };
        Arrays.asList(files).parallelStream().forEach(f -> {
            try {
                Arrays.asList(new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8)
                        .split("[\\P{L}]+")).stream().forEach(w ->
                        map.computeIfAbsent(w, k -> new HashSet<>()).add(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        assertNotNull(map.get("name"));
        assertEquals(2, map.get("name").size());
    }
}
