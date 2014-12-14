package de.galperin.javase8.capitel9;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E1 {

    public static void main(String[] args) {
        try {
            perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void perform() throws Exception {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(Paths.get(C9E1.class.getResource("/alice.txt").toURI()));
            try {
                out = new PrintWriter("/fake/alice.txt");
                while (in.hasNext()) out.println(in.next().toLowerCase());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
