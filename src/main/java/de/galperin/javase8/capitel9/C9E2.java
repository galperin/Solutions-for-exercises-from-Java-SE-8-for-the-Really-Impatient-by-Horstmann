package de.galperin.javase8.capitel9;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E2 implements Exercise {

    @Test
    @Override
    public void perform() {
        test(null, null, null, null);
    }

    @Test
    public void testFirst() {
        try {
            test(new RuntimeException("first"), null, null, null);
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("first", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    @Test
    public void testFirstAndSecond() {
        try {
            test(new RuntimeException("first"), new RuntimeException("second"), null, null);
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("first", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    @Test
    public void testSecondAndThird() {
        try {
            test(null, new RuntimeException("second"), new RuntimeException("third"), null);
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("second", e.getMessage());
            assertEquals(1, e.getSuppressed().length);
            assertEquals("third", e.getSuppressed()[0].getMessage());
        }
    }

    @Test
    public void testSecondAndThirdAndFifth() {
        try {
            test(null, new RuntimeException("second"), new RuntimeException("third"), new RuntimeException("fifth"));
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("second", e.getMessage());
            assertEquals(2, e.getSuppressed().length);
            assertEquals("third", e.getSuppressed()[0].getMessage());
            assertEquals("fifth", e.getSuppressed()[1].getMessage());
        }
    }

    @Test
    public void testThirdAndFifth() {
        try {
            test(null, null, new RuntimeException("third"), new RuntimeException("fifth"));
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("third", e.getMessage());
            assertEquals(1, e.getSuppressed().length);
            assertEquals("fifth", e.getSuppressed()[0].getMessage());
        }
    }

    @Test
    public void testFifth() {
        try {
            test(null, null, null, new RuntimeException("fifth"));
            fail("Must throw exception");
        } catch (RuntimeException e) {
            assertEquals("fifth", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    public void test(RuntimeException first, RuntimeException second,
                     RuntimeException third, RuntimeException fifth) throws RuntimeException {
        RuntimeException cachedException = null;
        try {
            if (first != null) {
                throw first;
            }
            try {
                if (second != null) {
                    throw second;
                }
            } catch (RuntimeException e) {
                cachedException = e;
                throw cachedException;
            } finally {
                try {
                    if (third != null) {
                        throw third;
                    }
                } catch (RuntimeException e) {
                    if (cachedException != null) {
                        cachedException.addSuppressed(e);
                    } else {
                        cachedException = e;
                        throw cachedException;
                    }
                }
            }
        } catch (RuntimeException e) {
            cachedException = e;
            throw cachedException;
        } finally {
            try {
                if (fifth != null) {
                    throw fifth;
                }
            } catch (RuntimeException e) {
                if (cachedException != null) {
                    cachedException.addSuppressed(e);
                } else {
                    throw e;
                }
            }
        }
    }

    public static void doPerform() throws Exception {
        Scanner in = null;
        PrintWriter out = null;
        Exception cachedException = null;
        try {
            in = new Scanner(Paths.get(C9E2.class.getResource("/alice.txt").toURI()));
            try {
                out = new PrintWriter("/fake/alice.txt");
                while (in.hasNext()) out.println(in.next().toLowerCase());
            } catch (Exception e) {
                cachedException = e;
                throw cachedException;
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception e) {
                    if(cachedException != null){
                        cachedException.addSuppressed(e);
                    } else {
                        cachedException = e;
                        throw cachedException;
                    }
                }
            }
        } catch (Exception e) {
            cachedException = e;
            throw cachedException;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                if(cachedException != null){
                    cachedException.addSuppressed(e);
                } else {
                    throw e;
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            doPerform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}