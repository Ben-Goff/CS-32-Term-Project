package edu.brown.cs.student.weekli.schedule;

import java.util.Calendar;

public class Block {

  private final Calendar startTime;
  private final long duration;
  private final long iD;
  private final boolean commitment;

  public Block(Calendar startTime, long duration, boolean commitment, long iD) {
    this.duration = duration;
    this.startTime = startTime;
    this.iD = iD;
    this.commitment = commitment;
  }


  public long getiD() {
    return iD;
  }

  public Calendar getStartTime() {
    return startTime;
  }

  public long getDuration() {
    return duration;
  }

  public boolean isCommitment() {
    return commitment;
  }
}
