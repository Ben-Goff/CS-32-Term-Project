package edu.brown.cs.student.weekli.schedule;

import java.util.List;
import java.util.UUID;

public class Block {

  private final long startTime;
  private final long duration;
  private final UUID iD;
  private final boolean commitment;
  private UUID projectID;
  private int projectPriority;

  public Block(long startTime, long duration, boolean commitment, UUID iD) {
    this.duration = duration;
    this.startTime = startTime;
    this.iD = iD;
    this.commitment = commitment;
  }


  public UUID getiD() {
    return iD;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getDuration() {
    return duration;
  }

  public boolean isCommitment() {
    return commitment;
  }

  public void makePartOfProject(UUID piD, int priority) {
    this.projectID = piD;
    this.projectPriority = priority;
  }
}
