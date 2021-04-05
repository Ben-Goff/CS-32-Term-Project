package edu.brown.cs.student.scheduler;

import org.junit.Test;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.assertEquals;

public class SchedulerTest {

    private Scheduler schedule;

    /**
     ** Tests calling an empty schedule.
     */
    @Test
    public void testEmptySchedule() {
        schedule = new Scheduler(Collections.emptyList(), Collections.emptyList());
        assertEquals(schedule.schedule(), Collections.emptyList());
    }

    /**
     ** Tests calling a schedule with no commitments and one task.
     */
    @Test
    public void testOneTask() throws IOException {
        long currentTime = (new Date()).getTime();
        Task t = new Task(currentTime + 1000000, currentTime + 1100000, 20000, "task", "task", 5000);
        schedule = new Scheduler(Collections.emptyList(), Collections.singletonList(t));
        List<Block> output = schedule.schedule();
        assertEquals(output.size(), 4);
        assertEquals(output.get(0).getEndTime() - output.get(0).getStartTime(), 5000);
        assertEquals(output.get(0).getiD(), t.getID());
        assertEquals(output.get(1).getEndTime() - output.get(1).getStartTime(), 5000);
        assertEquals(output.get(1).getiD(), t.getID());
        assertEquals(output.get(2).getEndTime() - output.get(2).getStartTime(), 5000);
        assertEquals(output.get(2).getiD(), t.getID());
        assertEquals(output.get(3).getEndTime() - output.get(3).getStartTime(), 5000);
        assertEquals(output.get(3).getiD(), t.getID());

    }

    /**
     ** Tests calling schedule with one commitment and no tasks.
     */
    @Test
    public void testOneCommitment() {
        long currentTime = (new Date()).getTime();
        Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment", "commitment", Optional.empty());
        schedule = new Scheduler(Collections.singletonList(c), Collections.emptyList());
        List<Block> output = schedule.schedule();
        assertEquals(output.size(), 1);
        assertEquals(output.get(0).getEndTime(), c.getEndDate());
        assertEquals(output.get(0).getStartTime(), c.getStartDate());

    }
}
