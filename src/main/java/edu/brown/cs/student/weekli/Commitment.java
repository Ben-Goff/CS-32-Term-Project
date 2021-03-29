package edu.brown.cs.student.weekli;

import java.util.Calendar;

/**
 * Represents a commitment in the schedule.
 */
public class Commitment implements Event{

  private Calendar startDate;
  private Calendar endDate;
  private long estTime;
  private String name;
  private String description;
  private final int category = 0;

  /**
   * Constructor.
   * @param start the start date
   * @param end the end date
   * @param name the name
   * @param description the description
   * @throws NumberFormatException if estTime < 0
   */
  public Commitment(Calendar start, Calendar end, String name, String description) throws NumberFormatException {
    this.startDate = start;
    this.endDate = end;
    this.name = name;
    this.description = description;
    long time = endDate.getTimeInMillis() - startDate.getTimeInMillis();
    if (time < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = time;
    }
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
}
