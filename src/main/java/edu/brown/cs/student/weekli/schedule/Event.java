package edu.brown.cs.student.weekli.schedule;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Interface to represent events.
 */
public interface Event {

  /**
   * Get the start date of the event.
   * @return the start date
   */
  long getStartDate();

  /**
   * Get the end date of the event.
   * @return the end date
   */
  long getEndDate();

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

  /**
   * Get the unique ID of the event.
   * @return the unique ID
   */
  UUID getID();

  /**
   * Get the Blocks corresponding with the event.
   * @return list of Blocks
   */
  List<Block> getBlocks();

}
