package edu.brown.cs.student.scheduler;

import org.junit.Test;
import java.util.*;
import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class CommitmentTest {


    @Test
    public void CreateNonRepeating() {
        UUID test = UUID.randomUUID();
        Commitment c = new Commitment(500000, 600000, "yo", "hello", test, Optional.empty(), "yellow");
        assertEquals(c.getID(), test);
        assertEquals(c.getStartDate(), 500000);
        assertEquals(c.getEndDate(), 600000);
        assertEquals(c.getColor(), "yellow");
        assertEquals(c.getName(), "yo");
        assertEquals(c.getDescription(), "hello");
        assertEquals(c.getRepeating(), Optional.empty());
    }

    @Test
    public void CreateRepeating() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.of(1000000L), "yellow");
        assertEquals(c.getStartDate(), 500000);
        assertEquals(c.getEndDate(), 600000);
        assertEquals(c.getColor(), "yellow");
        assertEquals(c.getName(), "yo");
        assertEquals(c.getDescription(), "hello");
        assertEquals((long) c.getRepeating().get(), 1000000L);
    }

    @Test
    public void getBlocksRangeInPresent() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.of(1000000L), "yellow");
        assertEquals(c.getBlocks(500000, 3600000).size(), 4);
        assertEquals(c.getBlocks(500000, 3600000).get(0).getStartTime(), 500000L);
    }

    @Test
    public void getBlocksRangeInPast() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.of(1000000L), "yellow");
        assertEquals(c.getBlocks(-9999999L, 3600000).size(), 14);
        assertEquals(c.getBlocks(-9999999L, 3600000).get(0).getStartTime(), -9500000L);
    }

    @Test
    public void getBlocksRangeInFuture() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.of(1000000L), "yellow");
        assertEquals(c.getBlocks(4400000, 8600000).size(), 5);
        assertEquals(c.getBlocks(4400000, 8600000).get(0).getStartTime(), 4500000L);
    }

    @Test
    public void getBlocksNonRepeatingInRange() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.empty(), "yellow");
        assertEquals(c.getBlocks(400000, 700000).size(), 1);
        assertEquals(c.getBlocks(500000, 3600000).get(0).getStartTime(), 500000L);
    }

    @Test
    public void getBlocksNonRepeatingNotInRange() {
        Commitment c = new Commitment(500000, 600000, "yo", "hello", Optional.empty(), "yellow");
        assertEquals(c.getBlocks(600001, 700001).size(), 0);
    }

}