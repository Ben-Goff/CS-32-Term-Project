package edu.brown.cs.student.weekli.schedule;

import java.util.List;
import java.util.stream.Collectors;

public class Project {

  private String name;
  private String description;
  private double progress;
  private List<Task> checkpoints;
  private List<Long> iD;

  public Project(List<Task> checkpoints, String name, String description) {
    this.checkpoints = checkpoints;
    this.iD = checkpoints.stream().map(t -> t.getID()).collect(Collectors.toList());
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

  public double getProgress() {
    return progress;
  }

  public String getDescription() {
    return description;
  }

  public List<Long> getiD() {
    return iD;
  }

  public List<Task> getCheckpoints() {
    return checkpoints;
  }
}
