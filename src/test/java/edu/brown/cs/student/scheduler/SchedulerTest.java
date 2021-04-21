package edu.brown.cs.student.scheduler;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.*;

import edu.brown.cs.student.weekli.schedule.*;

import static org.junit.Assert.*;

public class SchedulerTest {

  private Scheduler schedule;
  private long currentTime;

  public boolean noBlocksOverlap(List<Block> blocks) {
    blocks.sort(Comparator.comparingLong(Block::getStartTime));
    Block curBlock;
    Block nextBlock;
    for (int i = 0; i < blocks.size() - 1; i++) {
      curBlock = blocks.get(i);
      nextBlock = blocks.get(i + 1);
      if (curBlock.getEndTime() > nextBlock.getStartTime()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Updates current time.
   */
  @Before
  public void setUp() {
    currentTime = (new Date()).getTime();
  }

  /**
   ** Tests calling an empty schedule.
   */
  @Test
  public void testEmptySchedule() {
    schedule = new Scheduler(Collections.emptyList(), 0);
    assertEquals(schedule.schedule(Collections.emptyList(), 0, 0), Collections.emptyList());
  }

  /**
   ** Tests calling a schedule with no commitments and one task.
   */
  @Test
  public void testOneTask() throws IOException {
    setUp();
    Task t = new Task(currentTime + 1000000, currentTime + 1100000, 20000, "task", "task", 5000, "");
    schedule = new Scheduler(Collections.emptyList(), 0);
    List<Block> output = schedule.schedule(Collections.singletonList(t), 0, 0);
    assertTrue(noBlocksOverlap(output));
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
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
        "commitment", Optional.empty(), "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    List<Block> output = schedule.schedule(Collections.emptyList(), 0, currentTime + 1100000);
    assertTrue(noBlocksOverlap(output));
    assertEquals(output.size(), 1);
    assertEquals(output.get(0).getEndTime(), c.getEndDate());
    assertEquals(output.get(0).getStartTime(), c.getStartDate());
  }

  @Test
  public void testTwoTimeBins() {
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
        "commitment", Optional.empty(), "");
    Task t = new Task(currentTime + 500000, currentTime + 1600000, 1000000, "task", "task", 50000, "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    List<Block> output = schedule.schedule(Collections.singletonList(t), 0, 0);
    assertTrue(noBlocksOverlap(output));
    assertEquals(output.size(),21);
  }

  @Test
  public void twoTasks() {
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
        "commitment", Optional.empty(), "");
    Task t1 = new Task(currentTime + 500000, currentTime + 1600000, 500000, "task", "task", 50000, "");
    Task t2 = new Task(currentTime + 500000, currentTime + 1600000, 500000, "task1", "task1", 50000, "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    List<Task> tList = new ArrayList<>();
    tList.add(t1);
    tList.add(t2);
    List<Block> output = schedule.schedule(tList, 0, 0);
    assertTrue(noBlocksOverlap(output));
    assertEquals(output.size(),21);
  }

  @Test
  public void testBlank() {
    setUp();
    Commitment c = new Commitment(currentTime, currentTime + 480, "sleep", "sleep",
        Optional.of(1440L), "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    List<Task> tList = new ArrayList<>();
    List<Block> output = schedule.scheduleWithTime(tList, currentTime, currentTime + 3360L);
    assertTrue(noBlocksOverlap(output));
    assertEquals(output.size(), 3);
  }

  @Test
  public void testWigglePositive() {
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
            "commitment", Optional.empty(), "");
    Task t = new Task(currentTime + 500000, currentTime + 1700000, 1000000, "task", "task", 50000, "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    schedule.schedule(Collections.singletonList(t), 0, 9999999);
    assertEquals(100000, schedule.taskWiggle(t));
  }

  @Test
  public void testWiggleZero() {
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
            "commitment", Optional.empty(), "");
    Task t = new Task(currentTime + 500000, currentTime + 1600000, 1000000, "task", "task", 50000, "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    schedule.schedule(Collections.singletonList(t), 0, 9999999);
    assertEquals(0, schedule.taskWiggle(t));
  }

  @Test
  public void testWiggleNegative() {
    setUp();
    Commitment c = new Commitment(currentTime + 1000000, currentTime + 1100000, "commitment",
            "commitment", Optional.empty(), "");
    Task t = new Task(currentTime + 500000, currentTime + 1600000, 10000000, "task", "task", 50000, "");
    schedule = new Scheduler(Collections.singletonList(c), 0);
    boolean throwing = false;
    try {
      schedule.taskWiggle(t);
    } catch (RuntimeException e) {
      throwing = true;
    }
    assertTrue(throwing);
  }

}