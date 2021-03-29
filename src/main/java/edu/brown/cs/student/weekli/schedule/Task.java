package edu.brown.cs.student.weekli.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Task implements Event {

  private Calendar startDate;
  private Calendar endDate;
  private long estTime;
  private String name;
  private String description;
  private double progress;
  private final long iD;
  private final static int category = 1;
  private List<Block> taskBlocks;
  private long blockLength;

  /**
   * Constructor.
   * @param start the start date
   * @param end the end date
   * @param estTime length of time to complete task
   * @param name the name
   * @param description the description
   * @throws NumberFormatException if estTime < 0
   */
  public Task(Calendar start, Calendar end, long estTime, String name, String description, long blockLength) throws NumberFormatException {
    if (estTime < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = estTime;
    }
    this.startDate = start;
    this.endDate = end;
    this.name = name;
    this.description = description;
    this.progress = 0;
    this.iD = (new Date()).getTime();
    this.blockLength = blockLength;
    this.taskBlocks = blockBuilder();
  }

  public List<Block> blockBuilder() {
    int blockCount = (int) (Math.ceil(this.estTime / this.blockLength));
    Block[] taskBlocks = new Block[blockCount];
    Block temp;
    for(int i = 0; i < blockCount; i++) {
      temp = new Block(this.startDate, this.blockLength, false, this.iD);
      taskBlocks[i] = temp;
    }
    return Arrays.asList(taskBlocks);
  }

  /**
   * Get the start date of the event.
   *
   * @return the start date
   */
  @Override
  public Calendar getStartDate() {
    return startDate;
  }

  /**
   * Get the end date of the event.
   *
   * @return the end date
   */
  @Override
  public Calendar getEndDate() {
    return endDate;
  }

  /**
   * Get the event description.
   *
   * @return the description
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Get the event category.
   *
   * @return the event category
   */
  @Override
  public int getCategory() {
    return category;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Get the estimated time to complete event.
   *
   * @return the estimated time
   */
  @Override
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
public long getProgress() {
    return estTime;
    }

/**
 * Set the event progress.
 *
 */
public void setProgress(double prog) {
  setEstimatedTime((long) (getEstimatedTime() / (1 - this.progress) * (1 - prog)));
  this.progress = prog;
}

  /**
   * Gets the unique iD of the task
   * @return the unique iD
   */
  @Override
  public long getID() {
    return this.iD;
  }

  @Override
  public List<Block> getBlocks() {
    return this.taskBlocks;
  }

}
