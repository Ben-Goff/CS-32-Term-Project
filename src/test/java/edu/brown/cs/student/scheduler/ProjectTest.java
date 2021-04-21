package edu.brown.cs.student.scheduler;

import org.junit.Test;
import java.util.*;
import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class ProjectTest {


    @Test
    public void CreateWithID() {
        UUID test = UUID.randomUUID();
        Project p = new Project(test, "yo", "hello");
        assertEquals(p.getID(), test);
        assertEquals(p.getName(), "yo");
        assertEquals(p.getDescription(), "hello");
    }

    @Test
    public void CreateWithoutID() {
        Project p = new Project("yo", "hello");
        assertTrue(p.getID().toString().length() > 0);
        assertEquals(p.getName(), "yo");
        assertEquals(p.getDescription(), "hello");
    }

}