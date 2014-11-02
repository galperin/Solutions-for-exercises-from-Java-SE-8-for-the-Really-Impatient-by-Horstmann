package de.galperin.javase8.capitel3;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * User: eugen
 * Date: 02.11.14
 */
public class C3E9 implements Exercise {

    class Person {
        private String firstname;
        private String lastname;

        Person(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }
    }

    @Test
    @Override
    public void perform() {
        Person[] persons = {
                new Person("John", "Green"),
                new Person(null, "Black"),
                new Person("Adam", "White")
        };
        Arrays.sort(persons, lexographicComparator("lastname", "firstname"));
        assertEquals("Black", persons[0].lastname);
        Arrays.sort(persons, lexographicComparator("firstname", "lastname"));
        assertEquals("White", persons[0].lastname);
    }

    public <T> Comparator<T> lexographicComparator(String... fieldNames) {
        return (x, y) -> {
            for (String fieldName : fieldNames) {
                try {
                    Field field = x.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object valueX = field.get(x);
                    Object valueY = field.get(y);
                    if (valueX == null && valueY == null) continue;
                    if (valueX == null || valueY == null) return valueX == null ? 1 : -1;
                    int compareResult = field.get(x).toString().compareTo(field.get(y).toString());
                    if (compareResult != 0) {
                        return compareResult;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return 0;
        };
    }
}


