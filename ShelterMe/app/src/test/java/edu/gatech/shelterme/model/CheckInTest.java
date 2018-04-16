package edu.gatech.shelterme.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Meggles on 4/15/2018.
 */

public class CheckInTest {
    private WorkerSocial test;

    @Before
    public void setUp() throws Exception {
        test = new WorkerSocial();
    }

    @Test
    public void testExceedCapacity() throws Exception {
        int exceed = test.goodCheckIn(4, -5);
        assertEquals(-1, exceed);
    }

    @Test
    public void testCheckInNone() throws Exception {
        int zero = test.goodCheckIn(0, 3);
        assertEquals(0, zero);
    }

    @Test
    public void testValid() throws Exception {
        int valid = test.goodCheckIn(4, 5);
        assertEquals(1, valid);
    }
}
