package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 13.12.14
 */
public class C8E12 implements Exercise {

    @Test
    @Override
    public void perform() {
        Math math = new Math();
        Class clazz = math.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            TestCase[] testCases = method.getAnnotationsByType(TestCase.class);
            assertTrue(testCases.length > 0);
            for (TestCase testCase : testCases) {
                assertEquals(testCase.expected(), math.factorial(testCase.params()));
            }
        }
    }

    class Math {

        @TestCase(params = 4, expected = 24)
        @TestCase(params = 0, expected = 1)
        public int factorial(int n) {
            int fact = 1;
            for (int i = 1; i <= n; i++) {
                fact *= i;
            }
            return fact;
        }
    }
}

@Repeatable(TestCases.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface TestCase {
    int params();

    int expected();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestCases {
    TestCase[] value();
}
