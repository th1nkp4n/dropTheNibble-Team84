package edu.gatech.shelterme;
import org.junit.Test;

import edu.gatech.shelterme.model.HomelessAgeVerify;

import static org.junit.Assert.*;


public class HomelessAgeTest {
    @Test
    public void invalidAgeTest() throws Exception {
        assertEquals("bad age!", HomelessAgeVerify.ageIsValid(-1));
    }

    @Test
    public void validAgeTest() throws Exception {
        assertEquals("good age!", HomelessAgeVerify.ageIsValid(1));
    }

    @Test
    public void newbornAgeTest() throws Exception {
        assertEquals("newborn!", HomelessAgeVerify.ageIsValid(0));
    }
}
