package edu.brown.cs.student.scheduler;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.*;

import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class TimeBinTest {

    private TimeBin bin;

    /**
     * Reset test Time Bin.
     */
    @Before
    public void setUp() {
        bin = new TimeBin(0, 1000000, 100000);
        assertEquals(bin.getEndTime(), 1000000);
        assertEquals(bin.getStartTime(), 0);
        assertEquals(bin.getBreakTime(), 100000);
    }

    @Test
    public void addOneTask() {
        Task t = new Task(0, 1000000, 500000, "", "", 250000, "");
        assertTrue(bin.addBlock(t));
    }

    @Test
    public void tooMuchSessionTime() {
        Task t = new Task(0, 500000000, 5000000, "", "", 2500000, "");
        assertFalse(bin.addBlock(t));
    }

    @Test
    public void failsBecauseBreakTime() {
        Task t = new Task(0, 1000000, 1000000, "", "", 1000000, "");
        assertFalse(bin.addBlock(t));
    }

    @Test
    public void addMultiple() {
        Task t = new Task(0, 500000, 100000, "", "", 50000, "");
        assertTrue(bin.addBlock(t));
        t = new Task(500000, 1000000, 100000, "", "", 50000, "");
        assertTrue(bin.addBlock(t));
    }

    @Test
    public void addMultipleOverlapping() {
        Task t = new Task(0, 1000000, 100000, "", "", 50000, "");
        assertTrue(bin.addBlock(t));
        t = new Task(0, 1000000, 100000, "", "", 25000, "");
        assertTrue(bin.addBlock(t));
    }

    @Test
    public void forceReplacement() {
        Task t = new Task(0, 1000000, 100000, "", "", 50000, "");
        assertTrue(bin.addBlock(t));
        assertTrue(bin.addBlock(t));
        assertTrue(bin.addBlock(t));
        t = new Task(0, 300000, 100000, "", "", 25000, "");
        assertTrue(bin.addBlock(t));
    }

}