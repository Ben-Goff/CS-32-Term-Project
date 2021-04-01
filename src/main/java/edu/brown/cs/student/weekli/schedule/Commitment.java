package edu.brown.cs.student.weekli.schedule;

import java.util.*;

/**
 * Represents a commitment in the schedule.
 */
public class Commitment {

  private long startDate;
  private long endDate;
  private long estTime;
  private String name;
  private String description;
  private final UUID iD;
  private final static int category = 0;
  private final Block commitBlock;
  private final Optional<Long> repeating;

  /**
   * Constructor.
   * @param start the start date
   * @param end the end date
   * @param name the name
   * @param description the description
   * @throws NumberFormatException if estTime < 0
   */
  //TODO: handle commitments happening in the past when schedules remade causing bins to be made in the past
  public Commitment(long start, long end, String name, String description, Optional<Long> repeating) throws NumberFormatException {
    this.iD = UUID.randomUUID();
    this.startDate = start;
    this.endDate = end;
    long time = endDate - startDate;
    if (time < 0) {
      throw new NumberFormatException("ERROR: Duration of event is negative.");
    } else {
      this.estTime = time;
    }
    this.name = name;
    this.repeating = repeating;
    this.description = description;
    this.commitBlock = new Block(this.startDate, this.estTime, this.endDate, this.iD);
  }

  /**
   * Get the start date of the event.
   *
   * @return the start date
   */
  public long getStartDate() {
    return this.startDate;
  }

  /**
   * Get the end date of the event.
   *
   * @return the end date
   */
  public long getEndDate() {
    return this.endDate;
  }

  /**
   * Get the event description.
   *
   * @return the description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Get the event category.
   *
   * @return the event category
   */
  public int getCategory() {
    return this.category;
  }

  /**
   * Gets the name of the event.
   *
   * @return the name of the event
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the estimated time to complete event.
   *
   * @return the estimated time
   */
  public long getEstimatedTime() {
    return this.estTime;
  }

  /**
   * Gets the unique iD of the commitment
   * @return the unique iD
   */
  public UUID getID() {
    return this.iD;
  }

  /**
   * Gets the blocks
   * @return
   */
  public List<Block> getBlocks() {
    List<Block> blocks = new ArrayList<>();
    if (this.repeating.isPresent()) {
      final long weekMillis = 604800000L * 5;
      final int repetitions = (int) Math.ceil(weekMillis / this.repeating.get());
      for (int i = 0; i < repetitions; i++) {
        long start = this.startDate + i*repeating.get();
        blocks.add(new Block(start, this.estTime, start + this.estTime, this.iD));
      }
      return blocks;
    } else {
      blocks = Collections.singletonList(this.commitBlock);
      return blocks;
    }
  }
}
