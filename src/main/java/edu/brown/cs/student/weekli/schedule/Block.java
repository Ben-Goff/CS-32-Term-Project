package edu.brown.cs.student.weekli.schedule;

import java.util.List;
import java.util.UUID;

public class Block {

  private final long startTime;
  private final long endTime;
  private final UUID iD;
  private UUID projectID = null;
  private int projectPriority;

  public Block(long startTime, long endTime, UUID iD) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.iD = iD;
  }


  public UUID getiD() {
    return iD;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void makePartOfProject(UUID piD, int priority) {
    this.projectID = piD;
    this.projectPriority = priority;
  }
}
