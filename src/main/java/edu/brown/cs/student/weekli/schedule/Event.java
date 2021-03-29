package edu.brown.cs.student.weekli.schedule;

import java.util.Calendar;
/**
 * Interface to represent events.
 */
public interface Event {

  /**
   * Get the start date of the event.
   * @return the start date
   */
  Calendar getStartDate();

  /**
   * Get the end date of the event.
   * @return the end date
   */
  Calendar getEndDate();

  /**
   * Get the event description.
   * @return the description
   */
  String getDescription();

  /**
   * Get the event category.
   * @return the event category
   */
  int getCategory();

  /**
   * Gets the name of the event.
   * @return the name of the event
   */
  String getName();

  /**
   * Get the estimated time to complete event.
   * @return the estimated time
   */
  long getEstimatedTime();

}
