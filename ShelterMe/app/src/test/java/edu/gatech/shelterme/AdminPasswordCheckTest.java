package edu.gatech.shelterme;

import org.junit.Test;

import edu.gatech.shelterme.model.AdminPasswordCheck;

import static org.junit.Assert.assertEquals;

/**
 * Created by chiwk on 4/15/2018.
 */

public class AdminPasswordCheckTest {
    @Test
    public void goodPassTest() throws Exception {
        assertEquals("Good Password", AdminPasswordCheck.passIsGood("4ppleS"));
    }
    @Test
    public void missingNumPassTest() throws Exception {
        assertEquals("Needs a number", AdminPasswordCheck.passIsGood("appleS"));
    }
    @Test
    public void missingCapPassTest() throws Exception {
        assertEquals("Needs a capital letter", AdminPasswordCheck.passIsGood("4pples"));
    }
    @Test
    public void missingBothPassTest() throws Exception {
        assertEquals("Needs a capital letter and a number", AdminPasswordCheck.passIsGood("apples"));
    }
}
