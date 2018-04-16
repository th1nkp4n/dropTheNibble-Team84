package edu.gatech.shelterme.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {
    private WorkerSocial user;

    @Before
    public void setUp() throws Exception {
        user = new WorkerSocial();
    }

    /**
     * Tests no social security number
     */
    @Test
    public void testNoNumber() {
        String message = user.correctSocial("");
        assertEquals("number is too short", message);
    }

    /**
     * Tests a too long social security number
     */
    @Test
    public void testTooLong() {
        String message = user.correctSocial("0123456789");
        assertEquals("number is too long", message);
    }

    /**
     * Tests a valid social security number
     */
    @Test
    public void testValidNumber() {
        String message = user.correctSocial("123456789");
        assertEquals("valid number", message);
    }
}