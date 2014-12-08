package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 08.12.14
 */
public class C8E9 implements Exercise {

    @Test
    @Override
    public void perform() {
        try {
            Stream<String> words = words(newScanner());
            assertTrue(words.count() > 0);
            Stream<String> lines = lines(newScanner());
            assertTrue(lines.count() > 0);
            IntStream ints = integers(newScanner());
            assertTrue(ints.count() > 0);
            DoubleStream doubs = doubles(newScanner());
            assertTrue(doubs.count() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scanner newScanner() throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(this.getClass().getResource("/alice.txt").toURI()));
        scanner.useDelimiter("[\\s.,]+");
        return scanner;
    }


    public Stream<String> words(Scanner scanner) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                scanner, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public Stream<String> lines(Scanner scanner) {
        Iterator<String> iter = new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return scanner.hasNextLine();
            }

            @Override
            public String next() {
                return scanner.nextLine();
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public IntStream integers(Scanner scanner) {
        PrimitiveIterator.OfInt iter = new PrimitiveIterator.OfInt() {
            @Override
            public boolean hasNext() {
                while (!scanner.hasNextInt() && scanner.hasNext()) scanner.next();
                return scanner.hasNext();
            }

            @Override
            public int nextInt() {
                return scanner.nextInt();
            }
        };
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

    public DoubleStream doubles(Scanner scanner) {
        PrimitiveIterator.OfDouble iter = new PrimitiveIterator.OfDouble() {
            @Override
            public boolean hasNext() {
                while (!scanner.hasNextDouble() && scanner.hasNext()) scanner.next();
                return scanner.hasNext();
            }

            @Override
            public double nextDouble() {
                return scanner.nextDouble();
            }
        };
        return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(
                iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

}
