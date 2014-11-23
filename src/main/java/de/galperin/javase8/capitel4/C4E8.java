package de.galperin.javase8.capitel4;

import de.galperin.javase8.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: eugen
 * Date: 22.11.14
 */
public class C4E8 implements Exercise, Initializable {

    @FXML private Name name;
    @FXML private Address address;

    @Test
    @Override
    public void perform() {
        try {
            FXMLLoader.load(getClass().getResource("/person.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assertNotNull(name);
        assertNotNull(address);
        assertEquals("John", name.getFirstname());
        assertEquals("Black", name.getLastname());
        assertEquals("Mainstr", address.getStreet());
        assertEquals("Maintown", address.getCity());
        assertEquals("12345", address.getPostalCode());
    }
}

