package edu.brown.cs.student.scheduler;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.*;

import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class BlockTest {


    @Test
    public void CreateBlock() {
        UUID test = UUID.randomUUID();
        Block b = new Block(0, 1000000, test, "yo", "hello", "green");
        assertEquals(b.getiD(), test);
        assertEquals(b.getStartTime(), 0);
        assertEquals(b.getEndTime(), 1000000);
        assertEquals(b.getColor(), "green");
        assertEquals(b.getName(), "yo");
        assertEquals(b.getDescription(), "hello");
    }

}