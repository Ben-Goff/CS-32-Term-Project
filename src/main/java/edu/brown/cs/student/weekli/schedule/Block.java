package edu.brown.cs.student.weekli.schedule;

import java.util.List;
import java.util.UUID;

public class Block {

  private final long startTime;
  private final long endTime;
  private final UUID iD;
  private final String name;
  private final String description;
  private final String color;

  public Block(long startTime, long endTime, UUID iD, String name, String description, String color) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.iD = iD;
    this.name = name;
    this.description = description;
    this.color = color;
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

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getColor() {
    return color;
  }

}
