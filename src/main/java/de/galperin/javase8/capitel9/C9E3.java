package de.galperin.javase8.capitel9;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;

/**
 * User: eugen
 * Date: 14.12.14
 */
public class C9E3 {

    public void process() throws FileNotFoundException, UnknownHostException {
        try {
            if(System.currentTimeMillis() % 2 == 0) throw new FileNotFoundException();
            else throw new UnknownHostException();
        } catch (FileNotFoundException | UnknownHostException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
