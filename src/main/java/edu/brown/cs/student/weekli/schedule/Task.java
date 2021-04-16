package edu.brown.cs.student.weekli.schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Task {

  private long startDate;
  private long endDate;
  private long estTime;
  private String color;
  private String name;
  private String description;
  private double progress;
  private final UUID iD;
  private final static int category = 1;
  private long sessionTime;
  private UUID projectiD;

  /**
   * Constructor.
   * @param start the start date
   * @param end the end date
   * @param estTime length of time to complete task
   * @param name the name
   * @param description the description
   * @throws NumberFormatException if estTime < 0
   */
  public Task(long start, long end, long estTime, String name, String description, long sessionTime, String color) throws NumberFormatException {
    if (estTime < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = estTime;
    }
    this.startDate = start;
    this.endDate = end;
    this.name = name;
    this.color = color;
    this.description = description;
    this.progress = 0;
    this.iD = UUID.randomUUID();
    this.sessionTime = sessionTime;
  }

  public Task(long start,
              long end,
              long estTime,
              String name,
              String description,
              double progress,
              UUID id,
              long sessionTime,
              UUID projectiD,
              String color) throws NumberFormatException {
    if (estTime < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = estTime;
    }
    this.progress = progress;
    this.startDate = start;
    this.endDate = end;
    this.name = name;
    this.description = description;
    this.projectiD = projectiD;
    this.iD = id;
    this.sessionTime = sessionTime;
    this.color = color;
  }


  /**
   * Get the start date of the event.
   *
   * @return the start date
   */
  public long getStartDate() {
    return startDate;
  }

  /**
   * Get the end date of the event.
   *
   * @return the end date
   */
  public long getEndDate() {
    return endDate;
  }

  /**
   * Get the event description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get the length of blocks that come from this task.
   *
   * @return the length
   */
  public long getSessionTime() {
    return sessionTime;
  }

  /**
   * Get the event category.
   *
   * @return the event category
   */
  public int getCategory() {
    return category;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  public String getName() {
    return name;
  }

  /**
   * Get the estimated time to complete event.
   *
   * @return the estimated time
   */
  public long getEstimatedTime() {
    return estTime;
  }

  /**
   * Set the estimated time to complete event.
   *
   */
  public void setEstimatedTime(long time) {
    this.estTime = time;
  }

/**
 * Get the progress of the event.
 *
 * @return the progress
 */
public double getProgress() {
    return progress;
    }

/**
 * Set the event progress.
 *
 */
public void setProgress(double prog) {
  double temp = prog;
  if (temp == 1) {
    temp = 0.999;
  }
  setEstimatedTime((long) (getEstimatedTime() / (1 - this.progress) * (1 - temp)));
  this.progress = temp;
}

  /**
   * Gets the unique iD of the task
   * @return the unique iD
   */
  public UUID getID() {
    return this.iD;
  }

  public void addProjectID(UUID projID) throws ClassNotFoundException, SQLException {
    this.projectiD = projID;
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep =
        conn.prepareStatement("UPDATE tasks SET project = '" + projID.toString() + "' WHERE tasks.id = '" + this.getID().toString() + "';");
    prep.executeUpdate();
  }

  public UUID getProjectID() {
    return projectiD;
  }

  public String getColor() {
    return color;
  }

  public int sessions() {
    return (int) Math.floor((double) this.estTime / this.sessionTime);
  }

  public void blockComplete() {
    double progToAdd = this.sessionTime / (getEstimatedTime() / (1.0 - this.progress));
    setProgress(progress + progToAdd);
  }

}
