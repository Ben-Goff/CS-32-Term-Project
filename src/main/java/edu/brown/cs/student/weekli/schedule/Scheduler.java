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
  private List<Commitment> commitments;
  private List<Block> commitmentBlocks;
  private NavigableSet<Task> tasks;
  private List<TimeBin> bins = new ArrayList<>();

  public Scheduler(List<Commitment> commitments, List<Task> tasks) {
    this.tasks = new TreeSet<>(Comparator.comparingLong(t -> t.getEndDate() - t.getStartDate() - t.getEstimatedTime()));
    this.tasks.addAll(tasks);
    this.commitments = commitments;
  }

  public List<Block> schedule() {
    this.globalStartTime = (new Date()).getTime();
    this.commitmentBlocks = this.commitments.stream().map(Commitment::getBlocks).flatMap(Collection::stream).filter(b -> b.getStartTime() > globalStartTime).collect(Collectors.toList());
    buildBins();
    placeTasks();
    List<Block> schedule = this.bins.stream().map(TimeBin::getBlocks).flatMap(Collection::stream).collect(Collectors.toList());
    schedule.addAll(commitmentBlocks);
    return schedule;
  }

  public void buildBins() {
    bins.clear();
    boolean validCommitments;
    Block currentCommitment;
    Block nextCommitment;
    this.bins.add(new TimeBin(globalStartTime, commitmentBlocks.get(0).getStartTime()));
    for (int i = 0; i < commitmentBlocks.size() - 1; i++) {
      currentCommitment = commitmentBlocks.get(i);
      nextCommitment = commitmentBlocks.get(i + 1);
      validCommitments = currentCommitment.getEndTime() < nextCommitment.getStartTime();
      if (!validCommitments) {
        //commitments are overlapping, schedule can't be made
        throw new RuntimeException("Commitments are overlapping");
      }
      this.bins.add(new TimeBin(currentCommitment.getEndTime(), nextCommitment.getStartTime()));
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
    return ((b.getEndTime() > t.getStartDate() && b.getEndTime() < t.getEndDate()) ||
            (b.getStartTime() > t.getStartDate() && b.getStartTime() < t.getEndDate()) ||
            (b.getStartTime() < t.getStartDate() && b.getEndTime() > t.getEndDate()));
  }

  public void setCommitments(List<Commitment> c) {
    this.commitments = c;
  }

  public void setTasks(List<Task> t) {
    this.tasks.clear();
    this.tasks.addAll(t);
  }

  public void addCommitment(Commitment c) {
    this.commitments.add(c);
  }

  public void addTask(Task t) {
    this.tasks.add(t);
  }
}
