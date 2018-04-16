package edu.gatech.shelterme.model;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by KKhosla on 4/16/18.
 */

public class EmailTest {
    @Test
    public void noEnding() throws Exception {
        assertEquals(false, EmailCheck.testEmail("test@test"));
    }
    @Test
    public void noPeriod() throws Exception {
        assertEquals(false, EmailCheck.testEmail("test@testcom"));
    }
    @Test
    public void noBeginning() throws Exception {
        assertEquals(false, EmailCheck.testEmail("@test.com"));
    }
    @Test
    public void noAt() throws Exception {
        assertEquals(false, EmailCheck.testEmail("testtest.com"));
    }
    @Test
    public void noDomain() throws Exception {
        assertEquals(false, EmailCheck.testEmail("test@.com"));
    }
    @Test
    public void validEmail() throws Exception {
        assertEquals(true, EmailCheck.testEmail("kkhosla7@gatech.edu"));
    }
}
