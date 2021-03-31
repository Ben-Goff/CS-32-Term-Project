package edu.brown.cs.student.weekli.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {

  private String name;
  private String description;
  private double progress;
  private List<Task> checkpoints;
  private UUID iD;

  public Project(String name, String description) {
    this.checkpoints = new ArrayList<>();
    this.iD = UUID.randomUUID();
    this.name = name;
    this.description = description;
    double total = 0;
    for (Task c : checkpoints) {
      total += c.getProgress();
    }
    this.progress = total / this.checkpoints.size();
  }

  public String getName() {
    return name;
  }

  public void updateProgress() {
    double total = 0;
    for (Task c : this.checkpoints) {
      total += c.getProgress();
    }
    this.progress = total / this.checkpoints.size();
  }

  public double getProgress() {
    return progress;
  }

  public String getDescription() {
    return description;
  }

  public UUID getiD() {
    return iD;
  }

  public List<Task> getCheckpoints() {
    return checkpoints;
  }

  public void addCheckpoint(Task checkpoint) {
    checkpoint.addProjectID(this.iD);
    for (Block b: checkpoint.getBlocks()) {
      b.makePartOfProject(this.iD, this.checkpoints.size());
    }
    this.checkpoints.add(checkpoint);
  }
}
