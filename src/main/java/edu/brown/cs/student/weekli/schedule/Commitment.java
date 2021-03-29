package edu.brown.cs.student.weekli.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a commitment in the schedule.
 */
public class Commitment implements Event {

  private Calendar startDate;
  private Calendar endDate;
  private long estTime;
  private String name;
  private String description;
  private final long iD;
  private final static int category = 0;
  private final Block commitBlock;

  /**
   * Constructor.
   * @param start the start date
   * @param end the end date
   * @param name the name
   * @param description the description
   * @throws NumberFormatException if estTime < 0
   */
  public Commitment(Calendar start, Calendar end, String name, String description) throws NumberFormatException {
    this.iD = (new Date()).getTime();
    this.startDate = start;
    this.endDate = end;
    long time = endDate.getTimeInMillis() - startDate.getTimeInMillis();
    if (time < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = time;
    }
    this.name = name;
    this.description = description;
    this.commitBlock = new Block(this.startDate, this.estTime, true, this.iD);
  }

  /**
   * Get the start date of the event.
   *
   * @return the start date
   */
  @Override
  public Calendar getStartDate() {
    return this.startDate;
  }

  /**
   * Get the end date of the event.
   *
   * @return the end date
   */
  @Override
  public Calendar getEndDate() {
    return this.endDate;
  }

  /**
   * Get the event description.
   *
   * @return the description
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Get the event category.
   *
   * @return the event category
   */
  @Override
  public int getCategory() {
    return this.category;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Get the estimated time to complete event.
   *
   * @return the estimated time
   */
  @Override
  public long getEstimatedTime() {
    return this.estTime;
  }

  /**
   * Gets the unique iD of the commitment
   * @return the unique iD
   */
  @Override
  public long getID() {
    return this.iD;
  }

  /**
   * Gets the blocks
   * @return
   */
  @Override
  public List<Block> getBlocks() {
    List<Block> blocks = Collections.singletonList(this.commitBlock);
    return blocks;
  }
}
