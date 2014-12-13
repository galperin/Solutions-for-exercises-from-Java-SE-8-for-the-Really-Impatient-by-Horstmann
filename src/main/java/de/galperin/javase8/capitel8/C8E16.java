package de.galperin.javase8.capitel8;

import de.galperin.javase8.Exercise;
import org.junit.Test;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: eugen
 * Date: 13.12.14
 */
public class C8E16 implements Exercise {

    @Test
    @Override
    public void perform() {
        Optional<Address> addressOptional = parse("Niagara Falls, NY 14305");
        assertTrue(addressOptional.isPresent());
        addressOptional.ifPresent(a -> {
            assertEquals("Niagara Falls", a.city);
            assertEquals("NY", a.state);
            assertEquals("14305", a.zipCode);
        });
    }

    public Optional<Address> parse(String in) {
        Pattern pattern = Pattern.compile("(?<city>[\\p{L} ]+),\\s*(?<state>[A-Z]{2})\\s*(?<zip>[\\d]{5}|[\\d]{9})");
        Matcher matcher = pattern.matcher(in);
        if (matcher.find()) {
            Address address = new Address(
                    matcher.group("city"),
                    matcher.group("state"),
                    matcher.group("zip")
            );
            return Optional.of(address);
        }
        return Optional.empty();
    }

    class Address {

        String city;
        String state;
        String zipCode;

        public Address(String city, String state, String zipCode) {
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
    }
}
