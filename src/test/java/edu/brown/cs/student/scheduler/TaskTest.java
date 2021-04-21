package edu.brown.cs.student.scheduler;

import org.junit.Test;
import java.util.*;
import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class TaskTest {


    @Test
    public void CreateWithID() {
        UUID test = UUID.randomUUID();
        UUID proj = UUID.randomUUID();
        Task t = new Task(0L, 1000000L, 400000L, "yo", "hello", 0.2, test, 100000L, proj, "blue");
        assertEquals(t.getStartDate(), 0);
        assertEquals(t.getProjectID(), proj);
        assertEquals(t.getColor(), "blue");
        assertEquals(t.getID(), test);
        assertEquals(t.getEndDate(), 1000000L);
        assertEquals(t.getSessionTime(), 100000L);
        assertEquals(t.getName(), "yo");
        assertEquals(t.getEstimatedTime(), 400000L);
        assertEquals(t.getProgress(), 0.2, 0.01);
        assertEquals(t.getDescription(), "hello");
    }

    @Test
    public void CreateWithoutID() {
        Task t = new Task(0L, 1000000L, 400000L, "yo", "hello", 100000L, "blue");
        assertEquals(t.getStartDate(), 0);
        assertEquals(t.getColor(), "blue");
        assertEquals(t.getEndDate(), 1000000L);
        assertEquals(t.getSessionTime(), 100000L);
        assertEquals(t.getName(), "yo");
        assertEquals(t.getEstimatedTime(), 400000L);
        assertEquals(t.getProgress(), 0, 0.01);
        assertEquals(t.getDescription(), "hello");
    }

    @Test
    public void SetTaskProgress() {
        Task t = new Task(0L, 1000000L, 400000L, "yo", "hello", 100000L, "blue");
        t.setProgress(0.25);
        assertEquals(t.getEstimatedTime(), 300000L);
        assertEquals(t.getProgress(), 0.25, 0.01);
    }

    @Test
    public void SetTaskBlockFinished() {
        Task t = new Task(0L, 1000000L, 500000L, "yo", "hello", 100000L, "blue");
        t.setProgress(0.2);
        t.blockComplete();
        assertEquals(t.getProgress(), 0.4, 0.01);
    }

}