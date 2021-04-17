package edu.brown.cs.student.weekli.schedule;

import java.util.*;
import java.util.stream.Collectors;

public class Scheduler {

  /*
  STEP 1: PLACE COMMITMENTS
  STEP 3: CREATE LAMBDAS
      overflow constraint
      schedule blocks withing start/end time constraints
  STEP 4: BUILD SCHEDULE BLOCK BY BLOCK, CHECKING LAMBDAS
   */
  private long globalStartTime;
  private long tasksEndTime;
  private List<Commitment> commitments;
  private List<Block> commitmentBlocks;
  private NavigableSet<Task> tasks;
  private long breakTime;
  private List<TimeBin> bins = new ArrayList<>();

  public Scheduler(List<Commitment> commitments, long breakTime) {
    this.commitments = commitments;
    this.breakTime = breakTime;
  }

  public List<Block> schedule(List<Task> tasksToSchedule, long startDONTUSE, long end) throws RuntimeException {
    this.globalStartTime = (new Date()).getTime();
    List<Long> endTimes = tasksToSchedule.stream().map(Task::getEndDate).sorted().collect(Collectors.toList());
    if (endTimes.size() > 0) {
      this.tasksEndTime = Math.max(endTimes.get(endTimes.size() - 1), end);
    } else {
      this.tasksEndTime = end;
    }
//    System.out.println("above commitblocks: " + globalStartTime);
//    System.out.println("above commitblocks: " + tasksEndTime);
    this.commitmentBlocks = this.commitments.stream().map(c -> c.getBlocks(globalStartTime, tasksEndTime)).flatMap(Collection::stream).sorted(Comparator.comparingLong(Block::getStartTime)).collect(Collectors.toList());
    for (Block b : commitmentBlocks) {
//      System.out.println(b.getName());
//      System.out.println(b.getStartTime());
//      System.out.println(b.getEndTime());
//      System.out.println();
    }
    this.tasks = new TreeSet<>(new TaskComparator());
    this.tasks.addAll(tasksToSchedule);
//    System.out.println("above bins");
    buildBins();
//    System.out.println("above tasks");
    placeTasks();
//    System.out.println("above stream");
    List<Block> schedule = this.bins.stream().map(TimeBin::getBlocks).flatMap(Collection::stream).collect(Collectors.toList());
    schedule.addAll(commitmentBlocks);
    return schedule;
  }

  public List<Block> scheduleWithTime(List<Task> tasksToSchedule, long start, long end) {
    List<Long> endTimes = tasksToSchedule.stream().map(Task::getEndDate).sorted().collect(Collectors.toList());
    if (endTimes.size() > 0) {
      this.tasksEndTime = Math.max(endTimes.get(endTimes.size() - 1), end);
    } else {
      this.tasksEndTime = end;
    }
    this.commitmentBlocks = this.commitments.stream().map(c -> c.getBlocks(start, tasksEndTime)).flatMap(Collection::stream).collect(Collectors.toList());
    this.tasks = new TreeSet<>(new TaskComparator());
    this.tasks.addAll(tasksToSchedule);
    buildBins();
    placeTasks();
    List<Block> schedule = this.bins.stream().map(TimeBin::getBlocks).flatMap(Collection::stream).collect(Collectors.toList());
    schedule.addAll(commitmentBlocks);
    return schedule;
  }

  public long taskWiggle(Task t) {
    long timeForCompletetion = t.getSessionTime() * t.sessions();
    long naiveWiggle = t.getEndDate() - t.getStartDate() - timeForCompletetion;
    List<Block> withinTask = this.commitmentBlocks.stream().filter(b -> !(b.getStartTime() > t.getEndDate()) && !(t.getStartDate() > b.getEndTime())).collect(Collectors.toList());
    long totalOverlap = withinTask.stream().mapToLong(b -> Math.min(b.getEndTime(), t.getEndDate()) - Math.max(b.getStartTime(), t.getStartDate())).sum();
    long smartWiggle = naiveWiggle - totalOverlap;
    if (smartWiggle < 0) {
      throw new RuntimeException("Task "+t.getName()+" can not be placed: Insufficient time");
    }
    return smartWiggle;
  }

  public void buildBins() throws RuntimeException {
    bins.clear();
    boolean validCommitments;
    Block currentCommitment;
    Block nextCommitment;
    if (commitmentBlocks.isEmpty()) {
      this.bins.add(new TimeBin(globalStartTime, tasksEndTime, breakTime));
    } else {
//      System.out.println("1");
      this.bins.add(new TimeBin(globalStartTime, commitmentBlocks.get(0).getStartTime(), breakTime));
//      System.out.println("2");
      int size = commitmentBlocks.size();
//      System.out.println("3");

      for (int i = 0; i < size - 1; i++) {
        currentCommitment = commitmentBlocks.get(i);
        nextCommitment = commitmentBlocks.get(i + 1);
        validCommitments = currentCommitment.getEndTime() < nextCommitment.getStartTime();
        if (!validCommitments) {
          //commitments are overlapping, schedule can't be made
//          System.out.println("4");
//          System.out.println(currentCommitment.getEndTime());
//          System.out.println(nextCommitment.getStartTime());
          System.out.println("threw commitment overlapping exception");
          throw new RuntimeException("Commitments are overlapping");
        }
        this.bins.add(new TimeBin(currentCommitment.getEndTime(), nextCommitment.getStartTime(), breakTime));
      }
      long endLastCommit = commitmentBlocks.get(size - 1).getEndTime();
      if (endLastCommit < tasksEndTime) {
        this.bins.add(new TimeBin(endLastCommit, tasksEndTime, breakTime));
      }
    }
  }

  public void placeTasks() {
    int index;
    int sessionsLeft;
    int lastPlaced = -2;
    int binCount;
    List<TimeBin> possibleBins;
    for (Task t : tasks) {
      index = -1;
      sessionsLeft = t.sessions();
      possibleBins = this.bins.stream().filter(b -> taskFitsInBin(t, b)).collect(Collectors.toList());
      binCount = possibleBins.size();
      if (binCount == 0) {
        throw new RuntimeException("tasks can't fit");
      }
      while(sessionsLeft > 0) {
        index = (index + 1) % binCount;
        if(possibleBins.get(index).addBlock(t)) {
          sessionsLeft--;
          lastPlaced = index;
        } else {
          if (index == lastPlaced) {
            throw new RuntimeException("tasks can't fit");
          }
        }
      }
    }
  }

  public boolean taskFitsInBin(Task t, TimeBin b) {
    boolean binAfterTask = b.getStartTime() > t.getEndDate();
    boolean taskAfterBin = t.getStartDate() > b.getEndTime();
    return !binAfterTask && !taskAfterBin;
  }

  public void setCommitments(List<Commitment> c) {
    this.commitments = c;
  }

  public void setTasks(List<Task> t) {
    this.tasks.clear();
    this.tasks.addAll(t);
  }

  public void setGlobalStartTime(long startTime) {
    globalStartTime = startTime;
  }

  public void addCommitment(Commitment c) {
    this.commitments.add(c);
  }

  public void addTask(Task t) {
    this.tasks.add(t);
  }

  public class TaskComparator implements Comparator<Task> {

    public TaskComparator() {

    }

    @Override
    public int compare(Task o1, Task o2) {
      if (taskWiggle(o1) < taskWiggle(o2)) {
        return -1;
      } else if (taskWiggle(o1) > taskWiggle(o2)) {
        return 1;
      } else {
        Random r = new Random();
        return r.nextBoolean() ? -1 : 1;
      }
    }
  }



}
